

package org.tavant.api.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.repository.UserRepository;

@Component
public class OMSUserDetailsService implements UserDetailsService {
	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        OMSUser omsUser = userRepository.findByUsername(username);

        if (omsUser == null) {
            // Not found...
            throw new UsernameNotFoundException(
                    "User " + username + " not found.");
        }

        if (omsUser.getRole() == null) {
            // No Roles assigned to user...
            throw new UsernameNotFoundException("User not authorized.");
        }
        
        OMSUserDetails userDetails = new OMSUserDetails(omsUser);

        return userDetails;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
