package com.abcignitecms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcignitecms.dto.BookingDTO;
import com.abcignitecms.exceptions.BookingAlreadyExistsException;
import com.abcignitecms.exceptions.CapacityExceededException;
import com.abcignitecms.exceptions.InvalidDateException;
import com.abcignitecms.model.Booking;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.model.Member;
import com.abcignitecms.repository.BookingRepository;
import com.abcignitecms.repository.ClassRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClassRepository classRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ClassRepository classRepository) {
        this.bookingRepository = bookingRepository;
        this.classRepository = classRepository;
    }

    public void createBooking(BookingDTO bookingDTO) {
    	// Step 1: Check if the class exists
        GymClass gymClass = classRepository.findByName(bookingDTO.getClassName());
        if (gymClass == null) {
            throw new InvalidDateException("Class not found: " + bookingDTO.getClassName());
        }
        
     // Step 2: Check if the participation date is within the class's start and end date
        if (bookingDTO.getParticipationDate().isBefore(gymClass.getStartDate()) || 
            bookingDTO.getParticipationDate().isAfter(gymClass.getEndDate())) {
            throw new InvalidDateException("Invalid participation date: " + bookingDTO.getParticipationDate());
        }
        
        // Step 3: Check if the member has already booked the class for the given date
        long existingBookings = bookingRepository.findBookingsByMemberAndClassAndDate(
            bookingDTO.getMemberName(), bookingDTO.getClassName(), bookingDTO.getParticipationDate()).size();
        if (existingBookings > 0) {
            throw new BookingAlreadyExistsException("Member already booked for this class on the given date");
        }
//        
//        if (gymClass != null && gymClass.getCapacity() > bookingRepository.countBookings(gymClass)) {
//            Booking booking = new Booking(new Member(bookingDTO.getMemberName(), ""), gymClass, bookingDTO.getParticipationDate());
//            bookingRepository.save(booking);
//        } else {
//            throw new IllegalArgumentException("Class capacity exceeded or class not found.");
//        }
     // Step 4: Check if class capacity is full
        if (bookingRepository.countBookings(gymClass) >= gymClass.getCapacity()) {
            throw new CapacityExceededException("Capacity exceeded for class: " + gymClass.getName());
        }

        // Step 5: Create and save the booking
        Booking booking = new Booking(new Member(bookingDTO.getMemberName(), ""), gymClass, bookingDTO.getParticipationDate());
        bookingRepository.save(booking);
    }
}