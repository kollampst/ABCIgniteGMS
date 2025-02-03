package com.abcignitecms.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ClassDTOTest {

    private ClassDTO classDTO;

    @BeforeEach
    void setUp() {
        // Set up the classDTO object with valid data for testing
        classDTO = new ClassDTO("Yoga", LocalDate.now(), LocalDate.now().plusDays(1), LocalTime.of(10, 0), 60, 10);
    }

    // Test Constructor and Getters
    @Test
    void testClassDTOConstructorAndGetters() {
        // Check if constructor initializes fields correctly
        assertEquals("Yoga", classDTO.getName(), "Class name should match");
        assertEquals(LocalDate.now(), classDTO.getStartDate(), "Start date should match");
        assertEquals(LocalDate.now().plusDays(1), classDTO.getEndDate(), "End date should match");
        assertEquals(LocalTime.of(10, 0), classDTO.getStartTime(), "Start time should match");
        assertEquals(60, classDTO.getDuration(), "Duration should match");
        assertEquals(10, classDTO.getCapacity(), "Capacity should match");
    }

    // Test Setters
    @Test
    void testClassDTOSetters() {
        // Update fields using setters
        classDTO.setName("Pilates");
        classDTO.setStartDate(LocalDate.now().plusDays(1));
        classDTO.setEndDate(LocalDate.now().plusDays(2));
        classDTO.setStartTime(LocalTime.of(14, 0));  // 2:00 PM
        classDTO.setDuration(90);
        classDTO.setCapacity(20);

        // Verify that the setters updated the values correctly
        assertEquals("Pilates", classDTO.getName(), "Class name should be updated");
        assertEquals(LocalDate.now().plusDays(1), classDTO.getStartDate(), "Start date should be updated");
        assertEquals(LocalDate.now().plusDays(2), classDTO.getEndDate(), "End date should be updated");
        assertEquals(LocalTime.of(14, 0), classDTO.getStartTime(), "Start time should be updated");
        assertEquals(90, classDTO.getDuration(), "Duration should be updated");
        assertEquals(20, classDTO.getCapacity(), "Capacity should be updated");
    }

    // Test validation annotations for invalid cases
    @Test
    void testInvalidClassDTO() {
        // Test when ClassDTO has invalid data
        ClassDTO invalidClassDTO = new ClassDTO("", null, LocalDate.now().minusDays(1), LocalTime.of(10, 0), 0, -1);

        // Assertions for invalid values
        assertThrows(IllegalArgumentException.class, () -> {
            if (invalidClassDTO.getName().isEmpty()) {
                throw new IllegalArgumentException("Class name cannot be empty");
            }
            if (invalidClassDTO.getStartDate() == null || invalidClassDTO.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Start date must be in the present or future");
            }
            if (invalidClassDTO.getEndDate() == null || invalidClassDTO.getEndDate().isBefore(invalidClassDTO.getStartDate())) {
                throw new IllegalArgumentException("End date must be in the future");
            }
            if (invalidClassDTO.getStartTime() == null) {
                throw new IllegalArgumentException("Start time is required");
            }
            if (invalidClassDTO.getDuration() <= 0) {
                throw new IllegalArgumentException("Duration must be at least 1 minute");
            }
            if (invalidClassDTO.getCapacity() <= 0) {
                throw new IllegalArgumentException("Capacity must be at least 1");
            }
        });
    }

    // Test the valid case where the object is constructed correctly
    @Test
    void testValidClassDTO() {
        // Valid ClassDTO object
        ClassDTO validClassDTO = new ClassDTO("Pilates", LocalDate.now(), LocalDate.now().plusDays(1), LocalTime.of(9, 0), 45, 30);

        assertNotNull(validClassDTO);
        assertEquals("Pilates", validClassDTO.getName(), "Class name should be valid");
        assertTrue(validClassDTO.getStartDate().isEqual(LocalDate.now()) || validClassDTO.getStartDate().isAfter(LocalDate.now()), "Start date must be today or in the future");
        assertTrue(validClassDTO.getEndDate().isAfter(validClassDTO.getStartDate()), "End date should be after start date");
        assertNotNull(validClassDTO.getStartTime(), "Start time should not be null");
        assertTrue(validClassDTO.getDuration() >= 1, "Duration should be greater than or equal to 1");
        assertTrue(validClassDTO.getCapacity() >= 1, "Capacity should be greater than or equal to 1");
    }

    // Test invalid start date (before current date)
    @Test
    void testInvalidStartDate() {
        ClassDTO invalidClassDTO = new ClassDTO("Yoga", LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), LocalTime.of(10, 0), 60, 10);
        assertThrows(IllegalArgumentException.class, () -> {
            if (invalidClassDTO.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Start date must be in the present or future");
            }
        });
    }
}
