package com.abcignitecms.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.abcignitecms.exceptions.BookingAlreadyExistsException;
import com.abcignitecms.dto.BookingDTO;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.model.Member;
import com.abcignitecms.repository.BookingRepository;
import com.abcignitecms.repository.ClassRepository;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingServiceTest {
	private BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private ClassRepository classRepository = Mockito.mock(ClassRepository.class);
    private BookingService bookingService = new BookingService(bookingRepository, classRepository);

    @Test
    void testCreateBookingDuplicateBooking() {
        // Prepare test data
        GymClass gymClass = new GymClass("Yoga Class", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(10, 0), 30, 20);
        Member member = new Member("Sai Tharun", "email@example.com");
        LocalDate participationDate = LocalDate.now().plusDays(1);
        BookingDTO bookingDTO = new BookingDTO("Sai Tharun", "Yoga Class", participationDate);
        
        // Simulate existing booking
        Mockito.when(bookingRepository.isBookingExist(member.getName(), gymClass, participationDate)).thenReturn(true);
        
        // Test exception
        BookingAlreadyExistsException exception = assertThrows(BookingAlreadyExistsException.class, () -> {
            bookingService.createBooking(bookingDTO);
        });
        
        assertEquals("This member already has a booking for the class on the specified date.", exception.getMessage());
    }
}
