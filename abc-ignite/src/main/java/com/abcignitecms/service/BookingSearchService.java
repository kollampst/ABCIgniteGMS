package com.abcignitecms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcignitecms.dto.BookingResponseDTO;
import com.abcignitecms.exceptions.InvalidDateException;
import com.abcignitecms.exceptions.NoBookingsFoundException;
import com.abcignitecms.model.Booking;
import com.abcignitecms.repository.BookingRepository;

@Service
public class BookingSearchService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingSearchService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingResponseDTO> searchBookings(String member, LocalDate startDate, LocalDate endDate) {
    	// Check if startDate is after endDate
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidDateException("Start date cannot be after end date.");
        }

        // Fetch bookings based on search criteria from the repository
        List<Booking> filteredBookings = bookingRepository.findBookings(member, startDate, endDate);

        // If no bookings are found, throw an exception
        if (filteredBookings.isEmpty()) {
            throw new NoBookingsFoundException("No bookings found for the given criteria.");
        }

        // Return filtered results as DTOs
        return filteredBookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getMember().getName(),
                        booking.getGymClass().getName(),
                        booking.getGymClass().getStartTime(),
                        booking.getParticipationDate()))
                .collect(Collectors.toList());
    }
    
    
}
