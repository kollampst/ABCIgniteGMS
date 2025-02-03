package com.abcignitecms.controller;

import com.abcignitecms.dto.BookingDTO;
import com.abcignitecms.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc; // Automatically injected MockMvc

    @MockBean
    private BookingService bookingService; // Automatically mocked

    @Test
    void createBooking_Success() throws Exception {
        BookingDTO bookingDTO = new BookingDTO("John Doe", "Yoga Class", LocalDate.of(2025, 2, 15));

        // Mock the service layer
        doNothing().when(bookingService).createBooking(any(BookingDTO.class));

        mockMvc.perform(post("/bookings")
                .contentType("application/json")
                .content("{\"memberName\":\"John Doe\",\"className\":\"Yoga Class\",\"participationDate\":\"2025-02-15\"}"))
                .andExpect(status().isOk()) // Should return 200 OK
                .andExpect(content().string("Booking successful"));

        verify(bookingService).createBooking(any(BookingDTO.class)); // Verify that service method was called
    }

    // Test for missing member name
    @Test
    void createBooking_MissingMemberName() throws Exception {
        String requestBody = "{\"memberName\":\"\",\"className\":\"Yoga Class\",\"participationDate\":\"2025-02-15\"}";

        mockMvc.perform(post("/bookings")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest()) // Should return 400 for validation error
                .andExpect(content().json("{\"memberName\":\"Member name cannot be empty\"}")); // Validation error message
    }

    // Test for past participation date
    @Test
    void createBooking_PastParticipationDate() throws Exception {
        String requestBody = "{\"memberName\":\"John Doe\",\"className\":\"Yoga Class\",\"participationDate\":\"2023-01-01\"}";

        mockMvc.perform(post("/bookings")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest()) // Should return 400 for validation error
                .andExpect(content().string("{\"participationDate\":\"Participation date must be in the future\"}")); // Should match the validation message
    }
}
