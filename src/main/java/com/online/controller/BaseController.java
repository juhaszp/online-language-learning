package com.online.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.entity.User;
import com.online.interfaces.UserServiceInterface;
import com.online.service.EmailService;

public class BaseController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private UserServiceInterface userService;
    
    private EmailService emailService;
    
    @Autowired
    private HttpServletRequest context;

	@Autowired
	public void setEmailService(EmailService emailService) {
		
		this.emailService = emailService;
	}
	
	@Autowired
	public void setUserService(UserServiceInterface userService) {
		
		this.userService = userService;
	}
	
	public UserServiceInterface getUserService() {
		
		return userService;
	}

	public EmailService getEmailService() {
		
		return emailService;
	}

	/**
	 * Get logged in User
	 * @return User object or null if it's not exist
	 */
	protected User getLoggedInUser() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = null;
		if (principal instanceof UserDetails)
			email = ((UserDetails)principal).getUsername();
		
		if (Optional.ofNullable(email).isEmpty())
			return null;
		else
			return userService.findByEmail(email);
	}
	
	/**
	 * Add javascript file to main fragment from specified name
	 * @param model Mobel object
	 * @param jsName Javascript name with or without extension
	 */
	protected void addJSFileAttributeToModel(Model model, String jsName) {
		
		model.addAttribute("javascript", getJsFileNameWithPath(jsName));
	}
	
	/**
	 * Add javascript file to main fragment from specified name
	 * @param modelAndView MobelAndView object
	 * @param jsName Javascript name with or without extension
	 */
	protected void addJSFileAttributeToModelAndView(ModelAndView modelAndView, String jsName) {
		
		modelAndView.addObject("javascript", getJsFileNameWithPath(jsName));
	}
	
	private String getJsFileNameWithPath(String jsName) {
		
		if (jsName.contains(".js"))
			return "/js/" + jsName;
		else
			return "/js/" + jsName + ".js";
	}
	
	/**
	 * Add javascript file to main fragment from request URL
	 * @param model Mobel object
	 */
	protected void addJSFileAttributeToModel(Model model) {
		
		model.addAttribute("javascript", getJSUrlFromContext());
	}
	
	/**
	 * Add javascript file to main fragment from request URL
	 * @param modelAndView MobelAndView object
	 */
	protected void addJSFileAttributeToModelAndView(ModelAndView modelAndView) {
		
		modelAndView.addObject("javascript", getJSUrlFromContext());
	}

	private String getJSUrlFromContext() {
		
		return "/js" + (String)context.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) + ".js";
	}
}