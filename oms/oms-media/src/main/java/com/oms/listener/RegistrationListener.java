package com.oms.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.tavant.api.auth.model.OMSUser;

import com.api.email.service.EmailService;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.registration.OnRegistrationCompleteEvent;
import com.oms.service.OMSUserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private OMSUserService service;

    @Autowired
    EmailService emailService;
    

    // API
/*    private  static final  String messagelogoutError="Sorry, error logging out";
    private  static final  String messagelogoutSucc="You logged out successfully";
    private  static final  String messageregSucc="You registered successfully. We will send you a confirmation message to your email account.";
    private  static final  String messageregError="An account for that username/email already exists. Please enter a different username.";

*/
    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) throws OMSSystemException{
        this.confirmRegistration(event);
    }
    @Transactional
    private void confirmRegistration(final OnRegistrationCompleteEvent event) throws OMSSystemException{
        final OMSUser user = event.getOMSUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
       // http://localhost:4200/#/login/fdsfdsfdsfsdfgsdfewrewtewfddfgdsff 
        final String confirmationUrl = event.getAppUrl() + "/#/login/" + token;
        String templatefile="/RegistrationEmailTemplate.html";
        Map<String,Object> templateKeyMatch=new HashMap<String,Object>();
        templateKeyMatch.put("url", confirmationUrl);
        templateKeyMatch.put("userName", user.getFirstName());
        final String subject = "OMS application notification: Registered email verification";
        try{
        emailService.sendEmailMessage(user.getEmailId(), templatefile,templateKeyMatch,subject);
        }catch(Exception ex){
        	ex.printStackTrace();
        	throw new OMSSystemException(Status.PARTIAL_SUCCESS.name(), HttpStatus.PARTIAL_CONTENT, 
        			confirmationUrl);
        }
    }


}
