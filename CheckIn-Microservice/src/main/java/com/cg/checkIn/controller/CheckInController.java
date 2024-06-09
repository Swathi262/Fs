package com.cg.checkIn.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.checkIn.exception.AlreadyCheckedInException;
import com.cg.checkIn.exception.BookingNotFoundException;
import com.cg.checkIn.model.CheckIn;
import com.cg.checkIn.service.CheckInService;

@RestController
@RequestMapping("/checkIn")
public class CheckInController {

	@Autowired
	CheckInService service;
//<<<<<<< HEAD

	// post endpoint  for checking in.
//	@PostMapping("/{bookingId}/{userName}")
//	public ResponseEntity<CheckIn> checkIn(@PathVariable String bookingId, @PathVariable String userName,
//			
//			@RequestBody List<String> seatNumbers) throws AlreadyCheckedInException, BookingNotFoundException {
//		
//=======
	
	@PostMapping("/{bookingId}/{userName}")
	public ResponseEntity<CheckIn> checkIn(@PathVariable String bookingId, @PathVariable String userName, @RequestBody List<String> seatNumbers) throws AlreadyCheckedInException, BookingNotFoundException {
//>>>>>>> e08c1da8496682ee9b4fd4a2282e7e66d2ba4400
		return new ResponseEntity<>(service.checkIn(bookingId, userName, seatNumbers), HttpStatus.OK);
	}

	//put endpoint to cancel check in.
	@PutMapping("/cancelCheckIn/{checkInId}")
	public ResponseEntity<CheckIn> cancelCheckIn(@PathVariable String checkInId) throws BookingNotFoundException {
		
		return new ResponseEntity<>(service.cancelCheckIn(checkInId), HttpStatus.OK);
	
	}

}
