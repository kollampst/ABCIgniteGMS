package com.abcignitecms.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.abcignitecms.exceptions.InvalidDateException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookingTest {
	@Test
	void testBookingConstructorAndGetters() {
	    Member member = new Member("Sai Tharun", "email@example.com");
	    GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(10, 0), 60, 30);
	    LocalDate participationDate = LocalDate.now().plusDays(1);
	    
	    Booking booking = new Booking(member, gymClass, participationDate);

	    assertEquals("Sai Tharun", booking.getMember().getName());
	    assertEquals("Yoga Class", booking.getGymClass().getName());
	    assertEquals(participationDate, booking.getParticipationDate());
	}
	
	@Test
	void testBookingSetters() {
	    Member member = new Member("Sai Tharun", "email@example.com");
	    GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(10, 0), 60, 30);
	    LocalDate participationDate = LocalDate.now().plusDays(1);
	    
	    Booking booking = new Booking(member, gymClass, participationDate);

	    Member newMember = new Member("Sahith C", "sahith@example.com");
	    GymClass newGymClass = new GymClass("Zumba Class", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(12, 0), 60, 30);
	    LocalDate newParticipationDate = LocalDate.now().plusDays(2);

	    booking.setMember(newMember);
	    booking.setGymClass(newGymClass);
	    booking.setParticipationDate(newParticipationDate);

	    assertEquals("Sahith C", booking.getMember().getName());
	    assertEquals("Zumba Class", booking.getGymClass().getName());
	    assertEquals(newParticipationDate, booking.getParticipationDate());
	}
	
	@Test
	void testNullValuesInBooking() {
		Member member = new Member("Sai Tharun", "email@example.com");
	    GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(10, 0), 60, 30);

	    // Test for null participation date
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Booking(member, gymClass, null);  // This should throw IllegalArgumentException
	    });
	}

	@Test
	void testInvalidParticipationDate() {
	    Member member = new Member("Sai Tharun", "email@example.com");
	    GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(10, 0), 60, 30);
	    LocalDate participationDateInThePast = LocalDate.now().minusDays(1);
	    
	    // Assuming a validation or exception in the service layer for past dates
	    assertThrows(InvalidDateException.class, () -> {
	        new Booking(member, gymClass, participationDateInThePast);
	    });
	}

}
