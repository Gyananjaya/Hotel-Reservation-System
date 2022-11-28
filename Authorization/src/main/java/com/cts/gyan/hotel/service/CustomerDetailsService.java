package com.cts.gyan.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.gyan.hotel.exception.CustomerNotFoundException;
import com.cts.gyan.hotel.model.CustomerDetails;
import com.cts.gyan.hotel.repository.CustomerRepo;

@Service
public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepo customerRepo;

	// this method returns the Customer object based on the emailAddress...
	// whose password will get checked with the password we provided in this Customer
	// object..
	// if match --> authenticated , if not match --> user not authenticated

	@Override
	public CustomerDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		
		CustomerDetails customerDetails = null;
		customerDetails = getCustomerByEmailAddress(emailAddress);
		if (customerDetails != null) {
			customerDetails = new CustomerDetails(getCustomerByEmailAddress(emailAddress));
		}

		return customerDetails;
	}

	
	public CustomerDetails getCustomerByEmailAddress(String emailAddress) throws CustomerNotFoundException {
		CustomerDetails customerDetails = null;
		customerDetails = customerRepo.getCustomerByEmailAddress(emailAddress);
		if (customerDetails == null) {
			throw new CustomerNotFoundException("Given Customer-Details does not exist in our Database!!");
		}
		return customerDetails;
	}

}