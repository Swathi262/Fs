package com.cg.bookingmicroservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.bookingmicroservice.model.Booking;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByUserName(String userName);

}
