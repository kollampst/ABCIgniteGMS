package com.abcignitecms.repository;

import com.abcignitecms.model.Booking;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.model.Member;
import com.abcignitecms.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryTest {

    private BookingRepository bookingRepository;
    private GymClass gymClass1;
    private GymClass gymClass2;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        bookingRepository = new BookingRepository();
        
        // Initialize GymClass objects
        gymClass1 = new GymClass("Yoga", LocalDate.now().plusDays(1), LocalDate.now().plusDays(10), LocalTime.of(9, 0), 60, 20);
        gymClass2 = new GymClass("Pilates", LocalDate.now().plusDays(2), LocalDate.now().plusDays(10), LocalTime.of(10, 0), 60, 20);
        
        // Initialize Member objects
        member1 = new Member("John", "");
        member2 = new Member("Jane", "");
        
        // Create Booking objects and save them to the repository
        bookingRepository.save(new Booking(member1, gymClass1, LocalDate.now().plusDays(1)));
        bookingRepository.save(new Booking(member2, gymClass2, LocalDate.now().plusDays(2)));
        bookingRepository.save(new Booking(member1, gymClass1, LocalDate.now().plusDays(2)));
    }

    @Test
    void testSave() {
        Booking booking = new Booking(member2, gymClass1, LocalDate.now().plusDays(3));
        bookingRepository.save(booking);

        // Assert that the repository size has increased by 1
        assertEquals(1, bookingRepository.findBookingsByMemberAndClassAndDate("Jane", "Yoga", LocalDate.now().plusDays(3)).size());
    }

    @Test
    void testCountBookings() {
        long count = bookingRepository.countBookings(gymClass1);
        assertEquals(2, count, "There should be 2 bookings for the 'Yoga' class.");
    }

    @Test
    void testFindBookingsByMemberAndClassAndDate() {
        List<Booking> bookings = bookingRepository.findBookingsByMemberAndClassAndDate("John", "Yoga", LocalDate.now().plusDays(1));
        assertEquals(1, bookings.size(), "There should be 1 booking for member 'John' in 'Yoga' class on the given date.");
    }

    @Test
    void testFindBookingsWithDateRange() {
        // Test bookings for member 'John' within a date range
        List<Booking> bookings = bookingRepository.findBookings("John", LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        assertEquals(2, bookings.size(), "There should be 2 bookings for member 'John' in the date range.");

        bookings = bookingRepository.findBookings("John", LocalDate.now(), LocalDate.now().plusDays(1));
        assertEquals(1, bookings.size(), "There should be 1 booking for member 'John' in the date range.");
    }

    @Test
    void testFindBookingsWithNullMember() {
        // Test bookings where member is null
        List<Booking> bookings = bookingRepository.findBookings(null, LocalDate.now(), LocalDate.now().plusDays(1));
        assertEquals(1, bookings.size(), "There should be 1 booking within the date range, regardless of member.");
    }
}
