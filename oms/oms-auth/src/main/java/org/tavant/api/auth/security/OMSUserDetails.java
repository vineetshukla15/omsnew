package org.tavant.api.auth.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.Role;

public class OMSUserDetails extends OMSUser implements UserDetails {

	private static final long serialVersionUID = 6295675423565719381L;

	public OMSUserDetails(OMSUser user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(super.getRole()
				.getRoleName()));
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !super.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !super.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !super.isCredentialsexpired();
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled();
	}

	@Override
	public Role getRole() {
		return super.getRole();
	}
}