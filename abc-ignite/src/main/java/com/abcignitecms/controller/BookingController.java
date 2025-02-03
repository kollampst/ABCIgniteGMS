package com.abcignitecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcignitecms.dto.BookingDTO;
import com.abcignitecms.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @Operation(summary = "Create a new booking for a class")
    @PostMapping
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok("Booking successful");
    }
}