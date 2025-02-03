package com.abcignitecms.controller;

import com.abcignitecms.service.BookingSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/search/bookings")
public class BookingSearchController {

    private final BookingSearchService bookingSearchService;

    @Autowired
    public BookingSearchController(BookingSearchService bookingSearchService) {
        this.bookingSearchService = bookingSearchService;
    }

    @GetMapping
    public ResponseEntity<?> searchBookings(
        @RequestParam(name = "member", required = false) String member,
        @RequestParam(name = "startDate", required = false) String startDate,
        @RequestParam(name = "endDate", required = false) String endDate) {

        try {
            // Parse the string parameters into LocalDate if present
            LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
            LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

            // Validate the date range
            if (start != null && end != null && start.isAfter(end)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Invalid date range");
            }

            // Call the service to perform the search and return the result
            return ResponseEntity.ok(bookingSearchService.searchBookings(member, start, end));
        } catch (DateTimeParseException e) {
            // Handle invalid date format and return a 400 response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid date format");
        }
    }
}
