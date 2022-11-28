package com.cts.gyan.hotel.controller;

import java.util.List;
import java.util.Optional;

//import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.gyan.hotel.model.AuthenticationResponse;
import com.cts.gyan.hotel.model.CustomerDetails;
import com.cts.gyan.hotel.model.Reservation;
import com.cts.gyan.hotel.model.Rooms;
//import com.cts.gyan.hotel.model.Rooms;
import com.cts.gyan.hotel.repository.CustomerRepo;
import com.cts.gyan.hotel.repository.ReservationRepo;
import com.cts.gyan.hotel.repository.RoomsRepo;
import com.cts.gyan.hotel.request.AuthenticationRequest;
import com.cts.gyan.hotel.request.DeleteRequest;
import com.cts.gyan.hotel.request.ReservationRequest;
//import com.cts.gyan.hotel.repository.RoomsRepo;
import com.cts.gyan.hotel.request.SearchRequest;
import com.cts.gyan.hotel.request.ViewRequest;
import com.cts.gyan.hotel.service.CustomerDetailsService;
import com.cts.gyan.hotel.service.JwtService;
import com.cts.gyan.hotel.service.ReservationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api") // Context Root
@CrossOrigin(origins = "*", exposedHeaders = "**")
@Slf4j
@Validated
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private CustomerDetailsService customerDetailsService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ReservationRepo reservationRepo;
	
	@Autowired
	private RoomsRepo roomsRepo;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomerRepo customerRepo;

	
	@GetMapping("/working")
	public ResponseEntity<String> working() { // for checking hotel reservation [PERMITTED FOR ALL]
		return new ResponseEntity<String>("Hotel Reservation WORKING!!", HttpStatus.OK);
	}

	@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> generateJwt(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(0,"Invalid","Invalid","Invalid","Invalid", "Invalid","Invalid", false);
        ResponseEntity<AuthenticationResponse> response = null;
        // authenticating the User-Credentials
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmailAddress(), request.getPassword()));
            // else when it authenticates successfully
            final CustomerDetails customerDetails = customerDetailsService
                    .loadUserByUsername(request.getEmailAddress());
            authenticationResponse.setCustomerId(customerDetails.getCustomerId());
            authenticationResponse.setEmailAddress(customerDetails.getEmailAddress());
            authenticationResponse.setFirstName(customerDetails.getFirstName());
            authenticationResponse.setLastName(customerDetails.getLastName());
            authenticationResponse.setPhoneNumber(customerDetails.getPhoneNumber());
            authenticationResponse.setAddress(customerDetails.getAddress());
            authenticationResponse.setJwt(jwtService.generateToken(customerDetails));
            authenticationResponse.setValid(true);
            //final String jwt = jwtService.generateToken(customerDetails); // returning the token as response
            // test
            log.info("Authenticated Customer :: {}", customerDetails);
            response = new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.OK);
            log.info("Successfully Authenticated Customer!");
        } catch (Exception e) {
            log.error("{} !! info about request-body : {}", e.getMessage(), request);
            response = new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.FORBIDDEN);
        }
        log.info("-------- Exiting /authenticate");
        return response;
    }



	
	@PostMapping("/regis")
	public String SignUp(@RequestBody CustomerDetails customerDetails) {
		customerRepo.save(customerDetails);
		return "Customer Saved";
	}

	@GetMapping(value ="/room/all")
    public ResponseEntity<?> listAllRooms()
    {
        List<Rooms>  rooms = roomsRepo.findAll();
        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }
	
//	for getting all reserved roms
	@GetMapping(value ="/reserveroom/all")
    public ResponseEntity<?> listAllReserveRooms()
    {
        List<Reservation>  allreserverooms = reservationRepo.findAll();
        return new ResponseEntity<>(allreserverooms,HttpStatus.OK);
    }
//	end
	
	@GetMapping(value ="/room/available")
    public ResponseEntity<?> listAvailbaleRooms()
    {
        Iterable<Rooms>  availableRooms = roomsRepo.findByBookingStatusFalse();
        return new ResponseEntity<>(availableRooms,HttpStatus.OK);
    }
	
	/**
	 * reservation api
	 */
	@PostMapping("/reserve/add")
	public ResponseEntity<?> addReservation(@RequestBody ReservationRequest reservationRequest) {

		List<Rooms> updatedDetails = reservationService.addReservationService(reservationRequest);

		return new ResponseEntity<>(updatedDetails, HttpStatus.OK);

	}
	/**s
	 * Delete reservation
	 */
	@PostMapping("/reserve/delete")
	public ResponseEntity<?> deleteReservation(@RequestBody DeleteRequest deleteRequest) {

		Optional<Reservation> reservation = reservationService.deleteReservationService(deleteRequest.getReservationId());

		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}
	
//

}
