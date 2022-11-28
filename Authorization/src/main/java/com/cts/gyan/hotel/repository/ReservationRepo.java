package com.cts.gyan.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.gyan.hotel.model.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
	Optional<Reservation> findByCustomerId(int customerId);
}
