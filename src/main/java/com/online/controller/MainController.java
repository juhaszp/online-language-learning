package com.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online.entity.User;

@Controller
public class MainController extends BaseController {
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("page", "index");
		
		return "index";
	}
	
	@RequestMapping("/page1")
	public String page1(Model model) {
		model.addAttribute("page", "page1");
		
		return "page1";
	}
	
	@RequestMapping("/page2")
	public String page2(Model model) {
		model.addAttribute("page", "page2");
		
		return "page2";
	}
	
	@RequestMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("user", getLoggedInUser());

		return "profile";
	}
	
	@PostMapping("/profile_details_modify")
    public String profileDetailsModify(@ModelAttribute User user, Model model) {
		log.info("Felhasználó módosítása");

		log.debug(user.getFullName());
		log.debug(user.getEmail());
		
		String result = getUserService().modifyUser(user);
		model.addAttribute("result", result);
		
		if (!result.equals("user_modify_ok"))
			return "profile";
		else
			return "index";
    }
}