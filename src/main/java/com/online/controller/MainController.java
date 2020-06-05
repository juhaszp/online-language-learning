package com.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController extends BaseController {
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("page", "index");
		
		return "index";
	}
	
	@RequestMapping("/page1")
	public String bloggers(Model model) {
		model.addAttribute("page", "page1");
		
		return "page1";
	}
	
	@RequestMapping("/page2")
	public String stories(Model model) {
		model.addAttribute("page", "page2");
		
		return "page2";
	}
	
	@RequestMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("user", getLoggedInUser());

		return "profile";
	}
}