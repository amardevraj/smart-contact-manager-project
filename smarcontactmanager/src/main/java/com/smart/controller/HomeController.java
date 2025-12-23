package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home(Model model) {

		model.addAttribute("title", "Home -Smart Contact Manager ");

		return "home";
	}

	@RequestMapping(path = "/about", method = RequestMethod.GET)
	public String about(Model model) {

		model.addAttribute("title", "About -Smart Contact Manager ");

		return "about";
	}

	@RequestMapping(path = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {

		model.addAttribute("title", "Register -Smart Contact Manager ");
		model.addAttribute("user", new User());

		return "signup";
	}

	/*
	 * handler for register user 7044384127-2@ybl
	 */

	@RequestMapping(path = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid@ModelAttribute("user") User user,BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, Model model) {
		try {
			System.out.println(agreement);
			if (!agreement) {

				System.out.println("You have not agreed Terms and Condition");
				throw new Exception("You have not agreed Terms and Condition");
			}
			if(result.hasErrors()) {
				System.out.println("inside validato field "+result);
				model.addAttribute("user", user);
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println(user);
			User res = userRepository.save(user);
			model.addAttribute("user", new User());
			
			model.addAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			return "signup";

		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			model.addAttribute ("message", new Message("something Went wrong "+ e.getMessage(), "alert-danger"));
			return "signup";
		}
				
	}
	
	@RequestMapping(path="/login" , method = RequestMethod.GET)
	public String login(Model model, @RequestParam(value = "logout", defaultValue = "false") boolean logout) {
		model.addAttribute("logout", logout);
		
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout (Model model) {
		
		return "login";
	}
}
