package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repository;

	@RequestMapping("/dashboard")
	public String index(Model model,Principal principal) {
		String username = principal.getName();
		System.out.println(username);
		
		model.addAttribute("user",repository.getUserByUserName(username));
		model.addAttribute("title", "User- Dashboard");
		return "normal/dashboard";
	}
}
