package com.abcignitecms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.abcignitecms.dto.BookingResponseDTO;
import com.abcignitecms.exceptions.InvalidDateException;
import com.abcignitecms.exceptions.NoBookingsFoundException;
import com.abcignitecms.model.Booking;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.model.Member;
import com.abcignitecms.repository.BookingRepository;

public class BookingSearchServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingSearchService bookingSearchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchBookings_ValidInputs() {
        // Arrange
        Member member = new Member("Sai Tharun ", "Sai@example.com");
        GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(5), LocalTime.of(10, 0), 60, 20);
        Booking booking = new Booking(member, gymClass, LocalDate.now());

        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findBookings("Sai Tharun ", LocalDate.now(), LocalDate.now().plusDays(5))).thenReturn(bookings);

        // Act
        List<BookingResponseDTO> response = bookingSearchService.searchBookings("Sai Tharun ", LocalDate.now(), LocalDate.now().plusDays(5));

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Yoga Class", response.get(0).getClassName());
        assertEquals("Sai Tharun ", response.get(0).getMemberName());
    }

    @Test
    void testSearchBookings_StartDateAfterEndDate() {
        // Act & Assert
        assertThrows(InvalidDateException.class, () -> {
            bookingSearchService.searchBookings("Sai Tharun ", LocalDate.now().plusDays(5), LocalDate.now());
        });
    }

    @Test
    void testSearchBookings_NoBookingsFound() {
        // Arrange
        when(bookingRepository.findBookings("Sai Tharun ", LocalDate.now(), LocalDate.now().plusDays(5)))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NoBookingsFoundException.class, () -> {
            bookingSearchService.searchBookings("Sai Tharun ", LocalDate.now(), LocalDate.now().plusDays(5));
        });
    }

    @Test
    void testSearchBookings_WithNullStartDate() {
        // Arrange
        Member member = new Member("Sai Tharun ", "Sai@example.com");
        GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(5), LocalTime.of(10, 0), 60, 20);
        Booking booking = new Booking(member, gymClass, LocalDate.now());

        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findBookings("Sai Tharun ", null, LocalDate.now().plusDays(5))).thenReturn(bookings);

        // Act
        List<BookingResponseDTO> response = bookingSearchService.searchBookings("Sai Tharun ", null, LocalDate.now().plusDays(5));

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void testSearchBookings_WithNullEndDate() {
        // Arrange
        Member member = new Member("Sai Tharun ", "Sai@example.com");
        GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(5), LocalTime.of(10, 0), 60, 20);
        Booking booking = new Booking(member, gymClass, LocalDate.now());

        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findBookings("Sai Tharun ", LocalDate.now(), null)).thenReturn(bookings);

        // Act
        List<BookingResponseDTO> response = bookingSearchService.searchBookings("Sai Tharun ", LocalDate.now(), null);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void testSearchBookings_ExactStartAndEndDate() {
        // Arrange
        Member member = new Member("Sai Tharun ", "Sai@example.com");
        GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(5), LocalTime.of(10, 0), 60, 20);
        Booking booking = new Booking(member, gymClass, LocalDate.now());

        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findBookings("Sai Tharun ", LocalDate.now(), LocalDate.now())).thenReturn(bookings);

        // Act
        List<BookingResponseDTO> response = bookingSearchService.searchBookings("Sai Tharun ", LocalDate.now(), LocalDate.now());

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Yoga Class", response.get(0).getClassName());
    }
}
