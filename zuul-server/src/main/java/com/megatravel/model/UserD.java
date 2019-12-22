package com.megatravel.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class UserD implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private Integer active;
    private boolean isLocked;
    private boolean isExpired;
    private boolean isEnabled;
    private List<GrantedAuthority> grantedAuthorities;

	public UserD(String username, String [] authorities) {
		super();
		this.username = username;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
	}

	public UserD(String username, String password, Integer active, boolean isLocked, boolean isExpired,
			boolean isEnabled, String[] authorities) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
		this.isLocked = isLocked;
		this.isExpired = isExpired;
		this.isEnabled = isEnabled;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return active==1;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return  !isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return !isExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isEnabled;
	}
	
	public void setPassword(String password) {
        this.password = password;
    }

}
