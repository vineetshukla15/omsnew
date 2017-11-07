package org.tavant.api.auth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.Sources;

@Component
public class OMSAuthenticatoinProvider extends AbstractUserDetailsAuthenticationProvider{

    /**
     * The Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * A Spring Security UserDetailsService implementation based upon the
     * Account entity model.
     */
    @Autowired
    private OMSUserDetailsService userDetailsService;
    
/*    @Autowired
    private OMSUserService oMSUserService;*/
    @Autowired
    private LdapTemplate ldapTemplate;
    @Value("${authentication}")
    private String authentication;


    public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	@Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token) {
        if(authentication.equalsIgnoreCase("true")){
			logger.debug("> additionalAuthenticationChecks");
	        OMSUser omsUser=(OMSUser)userDetails;
	        PasswordEncoder encoder=new BCryptPasswordEncoder();
	        if (token.getCredentials() == null) {
				logger.error("Credentials may not be null.");
	        	throw new AuthenticationException();
	
	        }else if ((omsUser.getSource()==null||omsUser.getSource().equals(Sources.INTERNAL_SOURCE.getSource()))&&!authenticateFromLDAP(userDetails.getUsername(), (String) token.getCredentials())) {
	        	logger.error("Invalid credentials.");
	        	throw new AuthenticationException();
	
	        }else if (omsUser.getSource()!=null && omsUser.getSource().equals(Sources.REGISTERED_SOURCE.getSource())&&!encoder.matches((String)token.getCredentials(), omsUser.getPassword())) {
	        	logger.error("Invalid credentials.");
	        	throw new AuthenticationException();
	        }
	        logger.debug("< additionalAuthenticationChecks");
        }else{
        	logger.error("*************************** Oppss! Authentication skiped! ***************************");
        	logger.error("");
        	logger.error("Someone marked authentication not true in properties! If done explicitly please ignore otherwise check deployment configuration!");
        	logger.error("");
        	logger.error("*************************** Oppss! Authentication skiped! ***************************");
        }
    }

    @Override
    protected OMSUserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        logger.debug("> retrieveUser");

        OMSUserDetails userDetails = (OMSUserDetails) userDetailsService.loadUserByUsername(username);

        logger.debug("< retrieveUser");
        return userDetails;
    }  
    protected boolean authenticateFromLDAP(String userName, String password) throws AuthenticationException {
    	Filter filter = new EqualsFilter("sAMAccountName", userName);
		if(ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password)){
			return true;
		}
		return false;
    }
    
/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
    @Bean
	public LdapContextSource getContextSource() throws Exception{
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://10.129.135.230:3268");
		ldapContextSource.setBase("DC=in,DC=corp,DC=tavant,DC=com");
		ldapContextSource.setUserDn("IN\\hotstaruser");
		ldapContextSource.setPassword("June2017");
		return ldapContextSource;
	}
	
	@Bean(name ="ldapTemplate")
	public LdapTemplate ldapTemplate() throws Exception{
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		return ldapTemplate;
	}
}
