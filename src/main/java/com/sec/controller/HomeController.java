package com.sec.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sec.entity.User;
import com.sec.interfaces.UserService;
import com.sec.service.EmailService;


@Controller
public class HomeController {
	
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private UserService userService;
    
    private EmailService emailService;

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String home(Model model){
		model.addAttribute("page", "index");
		
		return "index";
	}
	
	@RequestMapping("/page1")
	public String bloggers(Model model){
		model.addAttribute("page", "page1");
		
		return "page1";
	}
	
	@RequestMapping("/page2")
	public String stories(Model model){
		model.addAttribute("page", "page2");
		
		return "page2";
	}
	
	@RequestMapping("/registration")
	public String registration(Model model){
		model.addAttribute("user", new User());
		
		return "registration";
	}

	@RequestMapping("/activation/{code}")
	public String activation(Model model, @PathVariable String code){
		String result = userService.userActivation(code);
		
		/*if (result.equals("ok"))
			return "activation";
		else
			return "error";*/
		
		return result.equals("ok") ? "activation" : "error";
	}
	
//	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	@PostMapping("/reg")
    public String reg(@ModelAttribute User user, Model model) {
		log.info("Uj user!");
//		emailService.sendMessage(user.getEmail());
		log.debug(user.getFullName());
		log.debug(user.getEmail());
		log.debug(user.getPassword());
		String result = userService.registerUser(user);
		model.addAttribute("result", result);
		
		if (!result.equals("ok"))
		{
			return "registration";
		}
		else
			return "auth/login";
    }

}
