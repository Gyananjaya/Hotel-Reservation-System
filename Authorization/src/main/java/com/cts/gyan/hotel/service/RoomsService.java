package com.cts.gyan.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.gyan.hotel.repository.RoomsRepo;

@Service
public class RoomsService {
	@Autowired
	private RoomsRepo roomsRepo;
}
