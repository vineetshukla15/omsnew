package com.oms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.Role;

import com.api.email.service.EmailService;
import com.oms.commons.utils.SearchUtil;
import com.oms.dao.UserDAO;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.UserRoleVO;
import com.oms.model.VerificationToken;
import com.oms.model.specification.UserSpecification;
import com.oms.repository.OMSUserRepository;
import com.oms.service.AuthenticationService;
import com.oms.service.OMSUserService;
import com.oms.service.TokenService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;
import com.oms.viewobjects.Sources;
import com.oms.viewobjects.UserDetailsVO;


@Component
public class OMSUserServiceImpl implements OMSUserService {
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";
    /**
     * The Logger for this class.
     */
    //private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EmailService emailService;
    
    public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
     * The Spring DAO for Account entities.
     */
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TokenService tokenService;
    /**
     * The Spring Data repository for Role entities
     */
    @Autowired
	private AuthenticationService ldapAuthenticationService;

	@Autowired
	private OMSUserRepository userRepository;

    /**
     * Find and return all accounts
     * @return collection of all accounts
     */
    @Override
    public Collection<OMSUser> findAll() {
        Collection<OMSUser> accounts = userDAO.getAllUser();
        return accounts;
    }

    /**
     * Find user by username
     * @param username the username of the user
     * @return the user account
     */
    @Override
    public OMSUser findByUserId(long userId) {
        OMSUser account = userDAO.getUserById(userId);
        return account;
    }
    
    @Override
    public OMSUser findByUserName(String userName) {
        OMSUser account = userDAO.getUserByName(userName);
        return account;
    }
    
	@Override
	public List<OMSUser> findByRoleName(String roleName) {
		List<OMSUser> users = null;
		try {
			users = userDAO.getUserByRoleName(roleName);
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Role name is invalid",
					ex);
		}
		return users;
	}

    /**
     * Create a new user as simple user. Find the simple user role from the database
     * add assign to the many to many collection
     * @param user - new Account of user
     * @return - the created account
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public OMSUser saveUser(OMSUser user) {
        return userDAO.addUser(user);
    }

	@Override
	public List<OMSUser> getAllUser() {
		List<OMSUser> userList =  null;
		try {
			userList = userRepository.findByDeletedFalse();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, "Unable to retrive User information",ex);
		}
		return userList;
	}

	@Override
	public OMSUser addUser(OMSUser user) throws OMSSystemException {
		List<OMSUser> ldapUsers = ldapAuthenticationService.searchUserByLoginName(user.getUsername());
		if(ldapUsers == null || ldapUsers.size()<1 || !user.equals(ldapUsers.get(0))){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "User " + user.getUsername() + " does not exist.");
		}
		List<OMSUser> userByEmail = userDAO.getUserByEmail(user.getEmailId());
		if (null != userByEmail && userByEmail.size() > 0) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"User with email: " + user.getEmailId() + " already exists in database");
		} else{
			try{
				return userDAO.addUser(user);
			}catch(Exception ex){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Unable to add this user, Please check object values !",ex);
			}
		}
	}

	@Override
	public OMSUser updateUser(OMSUser user) {
		OMSUser userResponse= null;
		try{
			userResponse = userDAO.addUser(user);
		}catch(Exception ex){
			
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,	"Unable to add this user, Please check object values !",ex);
		}
		return userResponse;
	}

	@Override
	public void deleteUser(long userId) {
		OMSUser user =  userRepository.findByUserId(userId);
		if(user != null){
			user.setDeleted(true);
			userRepository.save(user);
		}else{
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"User with Id {" + userId + "} does not exist or already deleted.");
		}
	}

	public PaginationResponseVO<OMSUser> searchUser(SearchRequestVO searchRequest){
		Page<OMSUser> pageResponse = null;
		PaginationResponseVO<OMSUser> response = null; 
		try {
			pageResponse = userRepository.findAll(UserSpecification.getUserSpecification(searchRequest),SearchUtil.getPageable(searchRequest));
			response= new PaginationResponseVO<OMSUser>(pageResponse.getTotalElements(), searchRequest.getDraw(), pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public VerificationToken createVerificationTokenForUser(final OMSUser user, final String token) {
	    final VerificationToken myToken = new VerificationToken(token, user);
	    return tokenService.createToken(myToken);
	}
	
    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenService.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        tokenService.createToken(vToken);
        return vToken;
    }

	@Override
	public OMSUser getOMSUserByToken(String verificationToken) {
        final VerificationToken token = tokenService.findByToken(verificationToken);
        if (token != null) {
            return token.getOMSUser();
        }
        return null;
	}
	
  
	@Override
	public String generateQRUrl(OMSUser user)throws UnsupportedEncodingException {
			return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmailId(), user.getPassword(), APP_NAME), "UTF-8");
	}

	@Override
	public UserDetailsVO validateVerificationToken(String token) {
		UserDetailsVO userDatails=new UserDetailsVO();
        final VerificationToken verificationToken = tokenService.findByToken(token);
        if (verificationToken == null) {
   
            userDatails.setTokenStatus(TOKEN_INVALID);
        }

        final OMSUser user = verificationToken.getOMSUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	tokenService.delete(verificationToken);
            userDatails.setTokenStatus(TOKEN_EXPIRED);
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userDAO.updateUser(user);
        userDatails.setTokenStatus(TOKEN_VALID);
        userDatails.setUsername(user.getUsername());
        userDatails.setFirstName(user.getFirstName());
        userDatails.setLastName(user.getLastName());
        userDatails.setEmailId(user.getEmailId());
        userDatails.setMobileNo(user.getMobileNo());
        return userDatails;
    
	}

	@Override
	public OMSUser registerNewOMSUserAccount(OMSUser user) throws OMSSystemException {
        if (userNameExist(user.getUsername())) {
        	throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,"There is an account with that email adress: " + user.getUsername());
        }
        user.setEnabled(false);
        user.setExpired(false);
        user.setDeleted(false);
        user.setCredentialsexpired(false);
        user.setCreatedBy(1l);
        user.setLocked(false);
 
        //user.setPassword(user.getPassword());
        Role role=new Role();
        role.setRoleId(1l);
        user.setRole(role);
        user.setSource(Sources.REGISTERED_SOURCE.getSource());
        //user.setRole()
        return userDAO.addUser(user);
    }
    private boolean userNameExist(final String username) {
        return userDAO.getUserByUsername(username)!= null;
    }
   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@SuppressWarnings("unused")
	@Override
	public void resendVerificationMail(String userEmail) {
        final OMSUser user = userDAO.getUserByUsername(userEmail);
       /* if (user != null) {
            final String token = UUID.randomUUID()
                .toString();
            createPasswordResetTokenForOMSUser(user, token);
            smtpMailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
 		}*/       
		
	}

	@Override
	public void updatePassword(OMSUser user) throws OMSSystemException{
		
		OMSUser omsuser = findByUserName(user.getUsername());
		if(omsuser==null){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"User " + user.getUsername() + " does not exist.");
		}else if(omsuser.isEnabled()){
			try{
			PasswordEncoder encoder=new BCryptPasswordEncoder();
			omsuser.setPassword(encoder.encode(user.getPassword()));
			saveUser(omsuser);
			emailService.send(omsuser.getEmailId(), "Password reset confirmation", "Hi <b>"+omsuser.getFirstName()+"</b>,<br> Your password reset has been successful. ");
			}catch(MessagingException ex){
				/*throw new OMSSystemException(Status.PARTIAL_SUCCESS.name(), HttpStatus.PARTIAL_CONTENT,
						"Passwaord set but email not sent",ex);*/
			}catch(MailAuthenticationException ex){
				/*throw new OMSSystemException(Status.PARTIAL_SUCCESS.name(), HttpStatus.PARTIAL_CONTENT,
						"Passwaord set but email not sent",ex);*/
			}
			catch(Exception ex){
				/*throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Unable to set password!",ex);*/
			}
			}else{
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"User " + user.getUsername() + " does not exist.");
		}
	}

	@Override
	public Map<String, List<UserRoleVO>> getAllUsersWithRole() {
		List<OMSUser> accounts = userDAO.getAllUser();
		Map<String, List<UserRoleVO>> responseMap=new HashMap<String, List<UserRoleVO>>();
		for(OMSUser user:accounts){
			if(user.getRole()!=null){
				String firstname=(user.getFirstName()!=null)?user.getFirstName():"";
				String space=" ";
				String lastname=(user.getLastName()!=null)?user.getLastName():"";
				if(responseMap.containsKey(user.getRole().getRoleName())){
					List<UserRoleVO> alreadyAddedUsers=responseMap.get(user.getRole().getRoleName());
					UserRoleVO uservo=new UserRoleVO();
					uservo.setName(firstname+space+lastname);
					uservo.setId(user.getId());
					alreadyAddedUsers.add(uservo);
					responseMap.put(user.getRole().getRoleName(), alreadyAddedUsers);
				}else{
					List<UserRoleVO> firstAddedUsers=new ArrayList<UserRoleVO>();
					UserRoleVO uservo=new UserRoleVO();
					uservo.setName(firstname+space+lastname);
					uservo.setId(user.getId());
					firstAddedUsers.add(uservo);
					responseMap.put(user.getRole().getRoleName(), firstAddedUsers);
				}
			}
		}
		return responseMap;
	}

}
