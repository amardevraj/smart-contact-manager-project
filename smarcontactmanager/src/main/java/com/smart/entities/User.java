package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Name Filed should not have null value.")
	@Size(min = 3, max = 12, message = "User Name should contains 3-12 characters!!")
	private String name;
	@Size(min = 5,  message = "Length of password should be in 5 characters.")
	@NotBlank(message = "password shold not be blank!!")
	private String password;
	
	@Column(unique = true)
	@Email(regexp = "^\\S+@\\S+\\.\\S+$")
	private String email;
	
	private String role;
	private boolean enabled;
	private String imangeUrl;
	@Column(length = 500)
	private String about;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Contact> contacts=new ArrayList<Contact>();
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImangeUrl() {
		return imangeUrl;
	}

	public void setImangeUrl(String imangeUrl) {
		this.imangeUrl = imangeUrl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", role=" + role
				+ ", enabled=" + enabled + ", imangeUrl=" + imangeUrl + ", about=" + about + ", contacts=" + contacts
				+ "]";
	}

}
