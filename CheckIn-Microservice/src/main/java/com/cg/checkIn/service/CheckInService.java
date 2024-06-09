package com.cg.checkIn.service;

import java.util.List;

import com.cg.checkIn.exception.AlreadyCheckedInException;
import com.cg.checkIn.exception.BookingNotFoundException;
import com.cg.checkIn.model.CheckIn;

public interface CheckInService {

	CheckIn checkIn(String bookingId, String userName, List<String> seatNumbers) throws AlreadyCheckedInException, BookingNotFoundException;

	CheckIn cancelCheckIn(String checkInId) throws BookingNotFoundException;
}
