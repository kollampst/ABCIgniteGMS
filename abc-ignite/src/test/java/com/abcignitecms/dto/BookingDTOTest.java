package com.abcignitecms.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookingDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Setup the validator for testing the constraints
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Test valid BookingDTO
    @Test
    void testValidBookingDTO() {
        BookingDTO bookingDTO = new BookingDTO(
                "John Doe",
                "Yoga Class",
                LocalDate.now().plusDays(1) // Valid future date
        );

        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);
        assertTrue(violations.isEmpty(), "Validation should pass for valid data");
    }

    // Test empty memberName field
    @Test
    void testMemberNameCannotBeEmpty() {
        BookingDTO bookingDTO = new BookingDTO(
                "", // Empty member name
                "Yoga Class",
                LocalDate.now().plusDays(1)
        );

        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);
        assertFalse(violations.isEmpty(), "Validation should fail for empty member name");
        assertEquals(1, violations.size(), "There should be exactly 1 validation error");
        assertEquals("Member name cannot be empty", violations.iterator().next().getMessage());
    }

    // Test empty className field
    @Test
    void testClassNameCannotBeEmpty() {
        BookingDTO bookingDTO = new BookingDTO(
                "John Doe",
                "", // Empty class name
                LocalDate.now().plusDays(1)
        );

        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);
        assertFalse(violations.isEmpty(), "Validation should fail for empty class name");
        assertEquals(1, violations.size(), "There should be exactly 1 validation error");
        assertEquals("Class name cannot be empty", violations.iterator().next().getMessage());
    }

    // Test null participationDate
    @Test
    void testParticipationDateCannotBeNull() {
        BookingDTO bookingDTO = new BookingDTO(
                "John Doe",
                "Yoga Class",
                null // Null participation date
        );

        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);
        assertFalse(violations.isEmpty(), "Validation should fail for null participation date");
        assertEquals(1, violations.size(), "There should be exactly 1 validation error");
        assertEquals("Participation date is required", violations.iterator().next().getMessage());
    }

    // Test past participationDate (should be in the future)
    @Test
    void testParticipationDateMustBeInTheFuture() {
        BookingDTO bookingDTO = new BookingDTO(
                "John Doe",
                "Yoga Class",
                LocalDate.now().minusDays(1) // Invalid past date
        );

        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);
        assertFalse(violations.isEmpty(), "Validation should fail for participation date in the past");
        assertEquals(1, violations.size(), "There should be exactly 1 validation error");
        assertEquals("Participation date must be in the future", violations.iterator().next().getMessage());
    }

    // Test participationDate is future or present
    @Test
    void testParticipationDateCanBeToday() {
        BookingDTO bookingDTO = new BookingDTO(
                "John Doe",
                "Yoga Class",
                LocalDate.now() // Valid today's date
        );

        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);
        assertTrue(violations.isEmpty(), "Validation should pass for today's date");
    }
}
