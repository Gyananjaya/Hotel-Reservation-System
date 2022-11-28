package com.cts.gyan.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.gyan.hotel.model.CustomerDetails;

public interface CustomerRepo extends JpaRepository<CustomerDetails, Integer> {
	
	@Query(value = "SELECT p FROM CustomerDetails p WHERE p.emailAddress = ?1")
	public CustomerDetails getCustomerByEmailAddress(String emailAddress);
	
}
