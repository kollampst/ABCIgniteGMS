package com.abcignitecms.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingResponseDTOTest {

    // Test constructor and getter methods
    @Test
    void testBookingResponseDTOConstructorAndGetters() {
        // Set up test data
        String memberName = "John Doe";
        String className = "Yoga Class";
        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM
        LocalDate participationDate = LocalDate.now(); // today's date
        
        // Create a BookingResponseDTO object
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO(memberName, className, startTime, participationDate);
        
        // Verify the constructor initializes the fields correctly
        assertEquals(memberName, bookingResponseDTO.getMemberName(), "Member name should match");
        assertEquals(className, bookingResponseDTO.getClassName(), "Class name should match");
        assertEquals(startTime, bookingResponseDTO.getStartTime(), "Start time should match");
        assertEquals(participationDate, bookingResponseDTO.getParticipationDate(), "Participation date should match");
    }

    // Test setter methods
    @Test
    void testBookingResponseDTOSetters() {
        // Create a BookingResponseDTO object
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("John Doe", "Yoga Class", LocalTime.of(10, 0), LocalDate.now());

        // Set new values using setters
        String newMemberName = "Jane Doe";
        String newClassName = "Pilates Class";
        LocalTime newStartTime = LocalTime.of(14, 30); // 2:30 PM
        LocalDate newParticipationDate = LocalDate.now().plusDays(1); // Tomorrow

        bookingResponseDTO.setMemberName(newMemberName);
        bookingResponseDTO.setClassName(newClassName);
        bookingResponseDTO.setStartTime(newStartTime);
        bookingResponseDTO.setParticipationDate(newParticipationDate);

        // Verify that the setters update the fields correctly
        assertEquals(newMemberName, bookingResponseDTO.getMemberName(), "Member name should be updated");
        assertEquals(newClassName, bookingResponseDTO.getClassName(), "Class name should be updated");
        assertEquals(newStartTime, bookingResponseDTO.getStartTime(), "Start time should be updated");
        assertEquals(newParticipationDate, bookingResponseDTO.getParticipationDate(), "Participation date should be updated");
    }

    // Test default constructor (optional, if you add one)
    @Test
    void testBookingResponseDTODefaultConstructor() {
        // Create a BookingResponseDTO object with default constructor
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO(null, null, null, null);

        // Verify that default values are null or as expected
        assertNull(bookingResponseDTO.getMemberName(), "Member name should be null");
        assertNull(bookingResponseDTO.getClassName(), "Class name should be null");
        assertNull(bookingResponseDTO.getStartTime(), "Start time should be null");
        assertNull(bookingResponseDTO.getParticipationDate(), "Participation date should be null");
    }
}

