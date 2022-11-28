package com.cts.gyan.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.gyan.hotel.model.Rooms;

public interface RoomsRepo extends JpaRepository<Rooms,Integer>{
	 @Query
	 public Iterable<Rooms> findByBookingStatusFalse();
	 @Query
	 public Iterable<Rooms> findByBookingStatusTrue();
	 public Iterable<Rooms> findByBookingStatusAndRoomType(boolean b, String string);
	 public List<Rooms> findByRoomId(int roomId);
}
