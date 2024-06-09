package com.cg.bookingmicroservice.service;

import com.cg.bookingmicroservice.dto.BookingDto;
import com.cg.bookingmicroservice.exception.*;
import com.cg.bookingmicroservice.model.Booking;

import java.util.List;

public interface BookingService {

    BookingDto bookFlight(Integer flightId, String userName, Integer noOfPersons) throws BookingFailedException, UserNameNotFoundException;

    BookingDto getBookingDetails(String bookingId) throws BookingNotFoundException;

    List<Booking> getBookingByUserName(String userName) throws BookingNotFoundException;

    BookingDto validateBooking(String bookingId, String userName) throws InvalidBookingException;

    String cancelFlight(String bookingId, String userName) throws BookingCancellationFailedException, BookingNotFoundException;

    BookingDto updateBookingCheckInStatus(String bookingId,String checkInId) throws BookingNotFoundException;

}
