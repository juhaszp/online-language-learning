package com.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.entity.User;

@Controller
public class HomeController extends BaseController {
	
	@RequestMapping("/registration")
	public ModelAndView registration() {
		
		ModelAndView modelAndView = new ModelAndView("registration");
	    modelAndView.addObject("user", new User());
	    
	    addJSFileAttributeToModelAndView(modelAndView, "validate-password");
	    
	    return modelAndView;
	}

	@RequestMapping("/activation/{code}")
	public String activation(Model model, @PathVariable String code) {
		
		String result = getUserService().userActivation(code);
		
		return result.equals("activation_ok") ? "activation" : "error";
	}
	
	@PostMapping("/reg")
    public String reg(@ModelAttribute User user, Model model) {
		
		log.info("Uj felhasználó felvétele");

		log.debug(user.getFullName());
		log.debug(user.getEmail());
		
		String result = getUserService().registerUser(user);
		model.addAttribute("result", result);
		
		if (!result.equals("registration_ok"))
			return "registration";
		else
			return "auth/login";
    }
	
	@RequestMapping("/forgotten_password")
	public ModelAndView forgottenPassword() {
		
		ModelAndView modelAndView = new ModelAndView("forgotten_password");
	    modelAndView.addObject("user", new User());
	    
	    return modelAndView;
	}

	@PostMapping("/for_pass")
    public String forPass(@ModelAttribute User user, Model model) {
		
		log.info("Új jelszó beállítása");
		String result = getUserService().forgottenPassword(user.getEmail());
		model.addAttribute("result", result);
		
		if (!result.equals("reset_password_ok"))
			return "forgotten_password";
		else
			return "auth/login";
    }
	
	@RequestMapping("/forgotten_password_activation/{code}")
	public String forgottenPasswordActivation(Model model, @PathVariable String code) {
		
		User user = getUserService().findByActivation(code);

		if (user ==  null)
			model.addAttribute("result", "invalid_activation_code");
		
		model.addAttribute("user", user);
		model.addAttribute("code", code);
		
		addJSFileAttributeToModel(model, "validate-password");
		
		return "forgotten_password_activation";
	}
	
	@PostMapping("/for_pass_activation")
    public String forPassActivation(@ModelAttribute User user, Model model) {
		
		//two_password_not_equals
		
		String result = getUserService().userForgottenPasswordActivation(user);
	
		if (result ==  null)
			model.addAttribute("result", "error_in_password_change");
		else
			model.addAttribute("result", result);

		if (!result.equals("set_password_ok"))
		{
			return "forgotten_password_activation";
		}
		else
			return "auth/login";
    }
}