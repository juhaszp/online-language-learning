package com.online.service;

import java.util.Base64;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.online.entity.Role;
import com.online.entity.User;
import com.online.interfaces.UserServiceInterface;
import com.online.repo.RoleRepository;
import com.online.repo.UserRepository;

@Service
public class UserService implements UserServiceInterface, org.springframework.security.core.userdetails.UserDetailsService {
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserRepository userRepository;

	private RoleRepository roleRepository;
	
	private EmailService emailService;

	private final String USER_ROLE = "USER";

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, EmailService emailService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		
		if (user == null)
			throw new UsernameNotFoundException(username);

		return new UserDetailsService(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByActivation(String code) {
		return userRepository.findByActivation(code);
	}

	@Override
	public String registerUser(User userToRegister) {
		if (userToRegister.getEmail().equals(""))
			return "empty_email";
		if (userToRegister.getFullName().equals(""))
			return "empty_name";
		if (userToRegister.getPassword().equals(""))
			return "empty_password";

		User userCheck = userRepository.findByEmail(userToRegister.getEmail());

		if (userCheck != null)
			return "user_email_already_exists";

		Role userRole = roleRepository.findByRole(USER_ROLE);
		
		if (userRole != null)
			userToRegister.getRoles().add(userRole);
		else
			userToRegister.addRoles(USER_ROLE);
		
		userToRegister.setEnabled(false);
		String generatedKey = generateKey();
		userToRegister.setActivation(generatedKey);
		userToRegister.setPassword(Base64.getEncoder().encodeToString(userToRegister.getPassword().getBytes()));
		userRepository.save(userToRegister);
		emailService.sendRegistrationMessage(userToRegister.getEmail(), generatedKey);

		return "registration_ok";
	}

	public String generateKey()
    {
		Random random = new Random();
		char[] word = new char[16];
		
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}

		return new String(word);
    }

	@Override
	public String userActivation(String code) {
		User user = userRepository.findByActivation(code);
		
		if (user == null)
		    return "no_result";
		
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		return "activation_ok";
	}

	@Override
	public String forgottenPassword(String email) {
		if (email.equals(""))
			return "empty_email";
		
		User user = userRepository.findByEmail(email);
		
		if (user == null)
			return "not_exists";
		
		String generatedKey = generateKey();
		
		//user.setEnabled(false);
		//user.setPassword("");
		user.setActivation(generatedKey);
		userRepository.save(user);
		
		emailService.sendForgottenPasswordMessage(email, generatedKey);
		
		return "reset_password_ok";
	}
	
	@Override
	public String userForgottenPasswordActivation(User userDetails) {
		if (Optional.ofNullable(userDetails.getEmail()).isEmpty())
			return "invalid_email";
		
		User user = userRepository.findByEmail(userDetails.getEmail());
		
		if (user == null)
		    return "not_exists_user";
		
		//user.setEnabled(true);
		user.setPassword(Base64.getEncoder().encodeToString(userDetails.getPassword().getBytes()));
		user.setActivation("");
		userRepository.save(user);
		
		return "set_password_ok";
	}
	
	@Override
	public String modifyUser(User userToModify) {
		if (userToModify.getFullName().equals(""))
			return "empty_name";
		if (userToModify.getPassword().equals(""))
			return "empty_password";

		User user = userRepository.findByEmail(userToModify.getEmail());

		user.setFullName(userToModify.getFullName());
		user.setPassword(Base64.getEncoder().encodeToString(userToModify.getPassword().getBytes()));
		userRepository.save(user);

		return "user_modify_ok";
	}
}
