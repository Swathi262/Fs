package com.cg.bookingmicroservice.controller;

import com.cg.bookingmicroservice.dto.BookingDto;
import com.cg.bookingmicroservice.exception.*;
import com.cg.bookingmicroservice.model.Booking;
import com.cg.bookingmicroservice.service.BookingService;

import jakarta.ws.rs.Path;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/bookFlight/{flightId}/{userName}/{noOfPersons}")
    public ResponseEntity<BookingDto> bookFlight(@PathVariable Integer flightId,@PathVariable String userName,@PathVariable Integer noOfPersons) throws BookingFailedException, UserNameNotFoundException {

        return new ResponseEntity<>(bookingService.bookFlight(flightId,userName,noOfPersons), HttpStatus.OK);

    }

    @GetMapping("/getBookingDetails/{bookingId}")
    public ResponseEntity<BookingDto> getBookingDetails(@PathVariable String bookingId) throws BookingNotFoundException {
        return new ResponseEntity<>(bookingService.getBookingDetails(bookingId),HttpStatus.OK);
    }

    @GetMapping("/getBookingByUserName/{userName}")
    public ResponseEntity<List<Booking>> getBookingByUserName(@PathVariable String userName) throws BookingNotFoundException {
        return new ResponseEntity<>(bookingService.getBookingByUserName(userName),HttpStatus.OK);
    }

    @GetMapping("/validateBooking/{bookingId}/{userName}")
    public ResponseEntity<BookingDto> validateBooking(@PathVariable String bookingId,@PathVariable String userName) throws InvalidBookingException {
        return new ResponseEntity<>(bookingService.validateBooking(bookingId,userName),HttpStatus.OK);
    }

    @PutMapping("/updateBookingCheckInStatus/{bookingId}/{checkInId}")
    public ResponseEntity<BookingDto> updateBookingCheckInStatus(@PathVariable String bookingId,@PathVariable String checkInId) throws BookingNotFoundException {
        return new ResponseEntity<>(bookingService.updateBookingCheckInStatus(bookingId,checkInId),HttpStatus.OK);
    }

    @DeleteMapping("/cancelFlight/{bookingId}/{userName}")
    public ResponseEntity<String> cancelFlight(@PathVariable String bookingId,@PathVariable String userName) throws BookingCancellationFailedException, BookingNotFoundException {
        return new ResponseEntity<>(bookingService.cancelFlight(bookingId,userName),HttpStatus.OK);
    }

}
