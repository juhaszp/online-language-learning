package com.online.service;

import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.online.entity.Role;
import com.online.entity.User;

public class UserDetailsService implements UserDetails {

	private static final long serialVersionUID = 3185970362329652822L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private User user;

	public UserDetailsService(User user) {
		
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<Role> roles = user.getRoles();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));

		return authorities;
	}

	@Override
	public String getPassword() {
		
		return new String(Base64.getDecoder().decode(user.getPassword()));
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return user.getEnabled();
	}
}