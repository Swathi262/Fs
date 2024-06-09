package com.cg.checkIn.service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.cg.checkIn.dto.BookingDto;
import com.cg.checkIn.dto.FlightDto;
import com.cg.checkIn.exception.AlreadyCheckedInException;
import com.cg.checkIn.exception.BookingNotFoundException;
import com.cg.checkIn.model.CheckIn;
import com.cg.checkIn.repository.CheckInRepository;

import reactor.core.publisher.Mono;

@Service
public class CheckInServiceImpl implements CheckInService {

	@Autowired
	WebClient webClient;
	@Autowired
	CheckInRepository repository;

	@Override
	public CheckIn checkIn(String bookingId, String userName, List<String> seatNumbers)
			throws AlreadyCheckedInException, BookingNotFoundException {

		// Getting booking details using booking id for which user has not checked in
		BookingDto bookingDto = webClient.get()
				.uri("http://localhost:8082/booking/validateBooking/" + bookingId + "/" + userName).retrieve()
				.bodyToMono(BookingDto.class).block();

		if (bookingDto == null) {
			throw new BookingNotFoundException("Booking with given id not found");
		}

		if (bookingDto.getCheckInStatus()) {
			throw new AlreadyCheckedInException("You have already checked in");
		}

		FlightDto responseEntity = webClient.put()
				.uri("http://localhost:8081/flight/update/" + bookingDto.getFlightId())
				.body(BodyInserters.fromValue(seatNumbers)).retrieve().bodyToMono(FlightDto.class).block();

		CheckIn checkIn = new CheckIn();
		checkIn.setCheckInId(UUID.randomUUID().toString());
		checkIn.setCheckInStatus("Success");
		checkIn.setFlightId(bookingDto.getFlightId());
		checkIn.setSeatsBooked(seatNumbers);
		checkIn.setUserName(userName);

		// making a rest api call to booking service to update checkin status along with
		// checkin id

		BookingDto bookingDto1 = webClient.put().uri(
				"http://localhost:8082/booking/updateBookingCheckInStatus/" + bookingId + "/" + checkIn.getCheckInId())
				.retrieve().bodyToMono(BookingDto.class).block();

		return repository.save(checkIn);

	}

	@Override
	public CheckIn cancelCheckIn(String checkInId) throws BookingNotFoundException {

		Optional<CheckIn> optionalCheckIn = repository.findById(checkInId);

		if (optionalCheckIn.isPresent()) {

			CheckIn checkIn = optionalCheckIn.get();

			List<String> seatNumbers = checkIn.getSeatsBooked();

			// Making a rest api call to flight microservice to restore the seats

			FlightDto updatedFlight = webClient.put()
					.uri("http://localhost:8081/flight/restoreFlightSeats/" + checkIn.getFlightId())
					.body(BodyInserters.fromValue(seatNumbers)).retrieve().bodyToMono(FlightDto.class).block();

			repository.delete(checkIn);

			return checkIn;

		}

		throw new BookingNotFoundException("Check in data with booking id not found");

	}

}
