package com.cg.checkIn.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.checkIn.model.CheckIn;

public interface CheckInRepository extends MongoRepository<CheckIn,String>{

	CheckIn findByUserName(String userName);

    CheckIn findByFlightId(int flightId);
}
