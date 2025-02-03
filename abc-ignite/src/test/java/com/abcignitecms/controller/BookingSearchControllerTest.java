package com.abcignitecms.controller;

import com.abcignitecms.dto.BookingResponseDTO;
import com.abcignitecms.service.BookingSearchService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookingSearchController.class)
public class BookingSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingSearchService bookingSearchService; // Mock the BookingSearchService

    @Test
    void searchBookings_NoParams() throws Exception {
        // Mock a list of BookingResponseDTO objects to be returned by the service
        BookingResponseDTO booking1 = new BookingResponseDTO("John Doe", "Yoga Class", LocalTime.of(10, 0), LocalDate.of(2025, 2, 1));
        BookingResponseDTO booking2 = new BookingResponseDTO("Jane Doe", "Pilates Class", LocalTime.of(11, 0), LocalDate.of(2025, 3, 1));
        List<BookingResponseDTO> bookings = Arrays.asList(booking1, booking2);

        // Mock the service to return the list of bookings
        when(bookingSearchService.searchBookings(null, null, null)).thenReturn(bookings);

        mockMvc.perform(get("/search/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("John Doe"))
                .andExpect(jsonPath("$[1].memberName").value("Jane Doe"))
                .andExpect(jsonPath("$[0].startTime").value("10:00:00"))
                .andExpect(jsonPath("$[1].startTime").value("11:00:00"));

        verify(bookingSearchService).searchBookings(null, null, null);
    }

    @Test
    void searchBookings_ByMember() throws Exception {
        String member = "John Doe";
        
        // Mock the service to return booking data for the specified member
        BookingResponseDTO booking = new BookingResponseDTO(member, "Yoga Class", LocalTime.of(10, 0), LocalDate.of(2025, 2, 1));
        List<BookingResponseDTO> bookings = Arrays.asList(booking);
        
        when(bookingSearchService.searchBookings(member, null, null)).thenReturn(bookings);

        mockMvc.perform(get("/search/bookings")
                        .param("member", member))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value(member))
                .andExpect(jsonPath("$[0].startTime").value("10:00:00"));

        verify(bookingSearchService).searchBookings(member, null, null);
    }

    @Test
    void searchBookings_ByDateRange() throws Exception {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        
        // Mock the service to return bookings within the date range
        BookingResponseDTO booking1 = new BookingResponseDTO("John Doe", "Yoga Class", LocalTime.of(10, 0), LocalDate.of(2025, 2, 1));
        List<BookingResponseDTO> bookings = Arrays.asList(booking1);
        
        when(bookingSearchService.searchBookings(null, startDate, endDate)).thenReturn(bookings);

        mockMvc.perform(get("/search/bookings")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("John Doe"))
                .andExpect(jsonPath("$[0].startTime").value("10:00:00"));

        verify(bookingSearchService).searchBookings(null, startDate, endDate);
    }

    @Test
    void searchBookings_InvalidDateRange() throws Exception {
        LocalDate startDate = LocalDate.of(2025, 12, 31);
        LocalDate endDate = LocalDate.of(2025, 1, 1);

        mockMvc.perform(get("/search/bookings")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isBadRequest()) // Expecting a 400 Bad Request due to invalid date range
                .andExpect(jsonPath("$").value("Invalid date range"));

        verify(bookingSearchService, never()).searchBookings(any(), any(), any());
    }

    @Test
    void searchBookings_InvalidDateFormat() throws Exception {
        String invalidDate = "2025-13-01"; // Invalid date (wrong month)

        mockMvc.perform(get("/search/bookings")
                        .param("startDate", invalidDate)
                        .param("endDate", invalidDate))
                .andExpect(status().isBadRequest()) // Expecting a 400 Bad Request due to invalid date format
                .andExpect(jsonPath("$").value("Invalid date format"));

        verify(bookingSearchService, never()).searchBookings(any(), any(), any());
    }
}
