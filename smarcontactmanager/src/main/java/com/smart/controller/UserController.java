package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repository;
	
	@ModelAttribute
	public void commonData(Model model, Principal principal) {
		
		String username=principal.getName();
		
		model.addAttribute("user", repository.getUserByUserName(username));
	}

	@RequestMapping("/dashboard")
	public String index(Model model,Principal principal) {
		
		model.addAttribute("title", "User-Dashboard");
		return "normal/dashboard";
	}
	
	
	//open add contact handler
	@RequestMapping( "/add-contact")
	public String openAddContactFrom(Model model) {
		model.addAttribute("title", "Add Contacts");
		model.addAttribute("contact",new Contact());
		return "/normal/add_contact_form";
	}
}
