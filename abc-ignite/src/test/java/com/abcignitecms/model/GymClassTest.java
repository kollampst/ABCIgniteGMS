package com.abcignitecms.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.abcignitecms.exceptions.CapacityExceededException;
import com.abcignitecms.exceptions.InvalidDateException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GymClassTest {
	 @Test
	    void testGymClassConstructor() {
	        // Arrange
	        String name = "Yoga";
	        LocalDate startDate = LocalDate.of(2025, 2, 15);
	        LocalDate endDate = LocalDate.of(2025, 3, 15);
	        LocalTime startTime = LocalTime.of(10, 0);
	        int duration = 60; // in minutes
	        int capacity = 30;

	        // Act
	        GymClass gymClass = new GymClass(name, startDate, endDate, startTime, duration, capacity);

	        // Assert
	        assertNotNull(gymClass);
	        assertEquals(name, gymClass.getName());
	        assertEquals(startDate, gymClass.getStartDate());
	        assertEquals(endDate, gymClass.getEndDate());
	        assertEquals(startTime, gymClass.getStartTime());
	        assertEquals(duration, gymClass.getDuration());
	        assertEquals(capacity, gymClass.getCapacity());
	    }

	    @Test
	    void testGettersAndSetters() {
	        // Arrange
	        GymClass gymClass = new GymClass("Zumba", LocalDate.now(), LocalDate.now().plusDays(10), LocalTime.of(9, 0), 45, 25);

	        // Test getters
	        assertEquals("Zumba", gymClass.getName());
	        assertEquals(LocalDate.now(), gymClass.getStartDate());
	        assertEquals(LocalDate.now().plusDays(10), gymClass.getEndDate());
	        assertEquals(LocalTime.of(9, 0), gymClass.getStartTime());
	        assertEquals(45, gymClass.getDuration());
	        assertEquals(25, gymClass.getCapacity());

	        // Test setters
	        gymClass.setName("Pilates");
	        gymClass.setStartDate(LocalDate.now().plusDays(1));
	        gymClass.setEndDate(LocalDate.now().plusDays(15));
	        gymClass.setStartTime(LocalTime.of(8, 30));
	        gymClass.setDuration(50);
	        gymClass.setCapacity(40);

	        assertEquals("Pilates", gymClass.getName());
	        assertEquals(LocalDate.now().plusDays(1), gymClass.getStartDate());
	        assertEquals(LocalDate.now().plusDays(15), gymClass.getEndDate());
	        assertEquals(LocalTime.of(8, 30), gymClass.getStartTime());
	        assertEquals(50, gymClass.getDuration());
	        assertEquals(40, gymClass.getCapacity());
	    }
	    
	    @Test
	    void testInvalidCapacity() {
	    	CapacityExceededException exception = assertThrows(CapacityExceededException.class, () -> {
	            new GymClass("Yoga", LocalDate.now(), LocalDate.now().plusDays(5), LocalTime.of(10, 0), 60, -5);
	        });
	        assertEquals("Capacity must be positive", exception.getMessage());
	    }

	    @Test
	    void testStartDateAfterEndDate() {
	        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> {
	            new GymClass("Yoga", LocalDate.now().plusDays(10), LocalDate.now(), LocalTime.of(10, 0), 60, 30);
	        });
	        assertEquals("Start date must not be after end date", exception.getMessage());
	    }

}
