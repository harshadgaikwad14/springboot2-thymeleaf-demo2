package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }
    @GetMapping("/starter")
    public String starter() {
        return "starter";
    }
   

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
    
    @GetMapping("/register")
    public String getRegister(Model model) {
    	 User user = new User();
         model.addAttribute("user", user);
        return "register";
    }
    
    @PostMapping("/register")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		
		
		
		
		//userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "register";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/user";
	}


}
