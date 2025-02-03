package com.abcignitecms.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abcignitecms.exceptions.CapacityExceededException;
import com.abcignitecms.exceptions.InvalidDateException;

public class GymClass {
	 private String name;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private LocalTime startTime;
	    private int duration; // in minutes
	    private int capacity;
		public GymClass(String name, LocalDate startDate, LocalDate endDate, LocalTime startTime, int duration,
				int capacity) {
			
			// Validating capacity
	        if (capacity <= 0) {
	            throw new CapacityExceededException("Capacity must be positive");
	        }

	        // Validating start and end dates
	        if (startDate.isAfter(endDate)) {
	            throw new InvalidDateException("Start date must not be after end date");
	        }
	        
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.startTime = startTime;
			this.duration = duration;
			this.capacity = capacity;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public LocalDate getStartDate() {
			return startDate;
		}
		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}
		public LocalDate getEndDate() {
			return endDate;
		}
		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}
		public LocalTime getStartTime() {
			return startTime;
		}
		public void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		@Override
		public String toString() {
		    return "GymClass{name='" + name + "', startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", duration=" + duration + ", capacity=" + capacity + "}";
		}

	    

}
