package com.oms.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.api.auth.model.OMSUser;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.VerificationToken;
import com.oms.registration.OnRegistrationCompleteEvent;
import com.oms.service.OMSUserService;
import com.oms.settings.errors.GenericResponse;
import com.oms.viewobjects.UserDetailsVO;

@RestController
@RequestMapping(value = "/registration",produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private OMSUserService userService;

/*    @Autowired
    private ISecurityOMSUserService securityOMSUserService;*/

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Environment env;

    public RegistrationController() {
        super();
    }

    // Registration

    @RequestMapping(method = RequestMethod.POST)
    @Auditable(actionType = AuditingActionType.USER_REGISTRATION)
    public GenericResponse registerOMSUserAccount(@RequestBody OMSUser userinfo, final HttpServletRequest request) throws OMSSystemException {
        LOGGER.debug("Registering user account with information: {}", userinfo);

        final OMSUser registered = userService.registerNewOMSUserAccount(userinfo);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public Map<String,Object> confirmRegistration(@RequestParam("token") final String token) throws UnsupportedEncodingException {
        Map<String,Object> resultMap=new HashMap<String, Object>();
    	UserDetailsVO result = userService.validateVerificationToken(token);
        
        if (result.getTokenStatus().equals("valid")) {
            final OMSUser user = userService.getOMSUserByToken(token);
            System.out.println(user);
            resultMap.put("message", "Your account verified successfully");
            resultMap.put("status", "success");
            resultMap.put("user", result);
            return resultMap;
        }else if(result.getTokenStatus().equals("invalidToken")){
        	resultMap.put("message", "This is invalid url, Please check your mail account and click on authentication url.");
        }else if(result.getTokenStatus().equals("expired"))	
        	resultMap.put("expired", "This token has been expired, Please check with support team oms-support@tavant.com");
        	resultMap.put("token", token);
        return resultMap;
    }

    // user activation - verification

    @RequestMapping(value = "/resendVerification", method = RequestMethod.GET)
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final OMSUser user = userService.getOMSUserByToken(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return new GenericResponse("We will send an email with a new registration token to your email account");
    }

    // Reset password
    @RequestMapping(value = "/setPassword", method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.RESET_PASSWORD)
    public Map<String,String> resetPassword(@RequestBody OMSUser user) throws OMSSystemException{
    	userService.updatePassword(user);
    	 Map<String,String> resultMap=new HashMap<String, String>();
    	 resultMap.put("status", "success");
    	 return resultMap;
    }

/*    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final long id, @RequestParam("token") final String token) {
        final String result = securityOMSUserService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }*/

/*    @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final OMSUser user = (OMSUser) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        userService.changeOMSUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
    }*/

    // change user password
/*    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse changeOMSUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final OMSUser user = userService.findOMSUserByEmail(((OMSUser) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeOMSUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }*/

/*    @RequestMapping(value = "/user/update/2fa", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse modifyOMSUser2FA(@RequestParam("use2FA") final boolean use2FA) throws UnsupportedEncodingException {
        final OMSUser user = userService.updateOMSUser2FA(use2FA);
        if (use2FA) {
            return new GenericResponse(userService.generateQRUrl(user));
        }
        return null;
    }*/

    // ============== NON-API ============

    private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, VerificationToken newToken, final OMSUser user) {
        final String confirmationUrl = contextPath + "/registration/confirmResendToken?token=" + newToken.getToken();
        final String message = messages.getMessage("message.resendToken", null, locale);
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final OMSUser user) {
        final String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + token;
        final String message = "Password reset has been successful";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, OMSUser user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmailId());
        email.setFrom("oms-support@Tavant.com");
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort();
    }

}
