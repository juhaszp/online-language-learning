package com.online.interfaces;

import com.online.entity.User;

public interface UserServiceInterface {
	
	public String registerUser(User user);

	public User findByEmail(String email);
	
	public User findByActivation(String code);

	public String userActivation(String code);
	
	public String forgottenPassword(String email);
	
	public String userForgottenPasswordActivation(User user);
}
