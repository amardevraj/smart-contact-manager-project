package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

	// applying pagination on contact details
	
	@Query("from Contact c where c.user.id=:userId")
	public List<Contact> findContactByUser(@Param("userId")Integer userId);
}
