package com.sec.service;

import java.util.Base64;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sec.entity.Role;
import com.sec.entity.User;
import com.sec.interfaces.UserService;
import com.sec.repo.RoleRepository;
import com.sec.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserRepository userRepository;

	private RoleRepository roleRepository;
	
	private EmailService emailService;

	private final String USER_ROLE = "USER";

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EmailService emailService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public String registerUser(User userToRegister) {
		if (userToRegister.getEmail().equals(""))
			return "emptyEmail";
		if (userToRegister.getFullName().equals(""))
			return "emptyName";
		if (userToRegister.getPassword().equals(""))
			return "emptyPassword";

		User userCheck = userRepository.findByEmail(userToRegister.getEmail());

		if (userCheck != null)
			return "alreadyExists";

		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			userToRegister.getRoles().add(userRole);
		} else {
			userToRegister.addRoles(USER_ROLE);
		}
		
		userToRegister.setEnabled(false);
		String generatedKey = generateKey();
		userToRegister.setActivation(generatedKey);
		userToRegister.setPassword(Base64.getEncoder().encodeToString(userToRegister.getPassword().getBytes()));
		userRepository.save(userToRegister);
		emailService.sendRegistrationMessage(userToRegister.getEmail(), generatedKey);
		//log.debug("Generated key: " + generatedKey);

		return "ok";
	}

	public String generateKey()
    {
		String key = "";
		Random random = new Random();
		char[] word = new char[16]; 
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		String toReturn = new String(word);
		//log.debug("random code: " + toReturn);
		return new String(word);
    }

	@Override
	public String userActivation(String code) {
		User user = userRepository.findByActivation(code);
		if (user == null)
		    return "noresult";
		
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		return "ok";
	}
	
	/** TODO remove **/
//	@PostConstruct
//	public void init(){
//		User user = new User();
//		user.setEmail("juhasz.peter@roxinvest.hu");
//		user.setEnabled(true);
//		user.setFullName("Juhász Péter");
//		user.setEmail("jpeter@vipmail.hu");
//		user.setEnabled(true);
//	//	user.setFullName("Nagy Béla");
//		//user.setPassword("1234");
//		user.setPassword("MTIzNA==");
//		user.addRoles(USER_ROLE);
//		user.setActivation("");
//		log.debug("----------------USER felvéve");
//		userRepository.save(user);
//	}

}
