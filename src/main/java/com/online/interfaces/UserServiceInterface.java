package com.online.interfaces;

import com.online.entity.User;

public interface UserServiceInterface {
	
	// Find user by ...
	public User findByEmail(String email);
	
	public User findByActivation(String code);

	// User operations 
	public String registerUser(User user);

	public String userActivation(String code);
	
	public String forgottenPassword(String email);
	
	public String userForgottenPasswordActivation(User user);
	
	public String modifyUser(User user);
}
