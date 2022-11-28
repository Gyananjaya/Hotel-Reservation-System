package com.cts.gyan.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.gyan.hotel.model.Reservation;
import com.cts.gyan.hotel.model.Rooms;
import com.cts.gyan.hotel.repository.ReservationRepo;
import com.cts.gyan.hotel.repository.RoomsRepo;
import com.cts.gyan.hotel.request.ReservationRequest;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepo reservationRepo;
	
	@Autowired
	private RoomsRepo roomsRepo;
	
	/**
	 * 
	 * @param reservationRequest
	 * @return
	 */
	
	public List<Rooms> addReservationService(ReservationRequest reservationRequest) {
			
			Reservation reservation = new Reservation();
			
			reservation.setCustomerId(reservationRequest.getCustomerId());
			reservation.setFromDate(reservationRequest.getFromDate());
			reservation.setToDate(reservationRequest.getToDate());
			reservation.setRoomId(reservationRequest.getRoomId());
	
			reservationRepo.save(reservation);
	       
			//Updating master table
			
			List<Rooms> masterDB = roomsRepo.findByRoomId(reservationRequest.getRoomId());
	
			Rooms selectedRoom = masterDB.get(0);
	
			selectedRoom.setBookingStatus(true);
			selectedRoom.setCheckIn(reservationRequest.getFromDate());
			selectedRoom.setCheckOut(reservationRequest.getToDate());
	
			roomsRepo.save(selectedRoom);
			
			List<Rooms> updatedDetails = roomsRepo.findByRoomId(reservationRequest.getRoomId());
			// a msg to be sent
			
			return updatedDetails;
		}
	
	
		/**
		 * 
		 * @param reservationId
		 * @return
		 */
	public Optional<Reservation> deleteReservationService(int reservationId) {
			
			Optional<Reservation> reservation = reservationRepo.findById(reservationId);
			
			reservationRepo.deleteById(reservationId);
			
			Reservation selectedReservation = null;
			
			if (reservation.isPresent()) {
				selectedReservation = reservation.get();
			}
	
			List<Rooms> masterDB = roomsRepo.findByRoomId(selectedReservation.getRoomId());
	
			Rooms selectedRoom = masterDB.get(0);
	
			selectedRoom.setBookingStatus(false);
			selectedRoom.setCheckIn(null);
			selectedRoom.setCheckOut(null);
	
			roomsRepo.save(selectedRoom);
			
			return reservation;
			
		}
	
}
