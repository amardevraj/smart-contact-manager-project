package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ContactRepository contactRepository;

	@ModelAttribute
	public void commonData(Model model, Principal principal) {

		String username = principal.getName();

		model.addAttribute("user", repository.getUserByUserName(username));
	}

	@RequestMapping("/dashboard")
	public String index(Model model, Principal principal) {

		model.addAttribute("title", "User-Dashboard");
		return "normal/dashboard";
	}

	// open add contact handler
	@RequestMapping("/add-contact")
	public String openAddContactFrom(Model model) {
		model.addAttribute("title", "Add Contacts");
		model.addAttribute("contact", new Contact());
		return "/normal/add_contact_form";
	}

	// processing add contact form
	@RequestMapping(path = "/process-contact", method = RequestMethod.POST)
	public String processContact(@ModelAttribute Contact contact, BindingResult result,
			@RequestParam("contactImage") MultipartFile file, Principal principal, Model model) {
		try {
			String username = principal.getName();
			User user = repository.getUserByUserName(username);
			if (file.isEmpty()) {
				System.out.println("File is Empty...");
				throw new Exception("User Should Upload an image of him ");
				
			} else {
				// upload file to folder and update name to contact
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
			}
			contact.setImage(file.getOriginalFilename());
			contact.setUser(user);
			user.getContacts().add(contact);
			User save = repository.save(user);
			System.out.println("contact got added to database");
			model.addAttribute("message", new Message("Contact Uploded Successfully", "alert-success"));
			System.out.println("Data: " + contact);
			return "/normal/add_contact_form";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("message", new Message("Something Went wrong " +"'"+ e.getMessage()+"'", "alert-danger"));
			return "/normal/add_contact_form";
		}
	}
	
	@RequestMapping("/show-contacts")
	public String showContacts(Model model, Principal principal) {
		User user = repository.getUserByUserName(principal.getName());
		List<Contact> contactList = contactRepository.findContactByUser(user.getId());
		
		model.addAttribute("title","show contacts");
		model.addAttribute("contacts",contactList);
		return "normal/show_contact";
	}
}
