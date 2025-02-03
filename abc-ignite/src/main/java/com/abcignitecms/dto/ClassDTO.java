package com.abcignitecms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClassDTO {
	@NotBlank(message = "Class name cannot be empty")
    private String name;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future")
    private LocalDate endDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

	public ClassDTO(@NotBlank(message = "Class name cannot be empty") String name,
			@NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date must be in the present or future") LocalDate startDate,
			@NotNull(message = "End date is required") @FutureOrPresent(message = "End date must be in the future") LocalDate endDate,
			@NotNull(message = "Start time is required") LocalTime startTime,
			@Min(value = 1, message = "Duration must be at least 1 minute") int duration,
			@Min(value = 1, message = "Capacity must be at least 1") int capacity) {
		super();
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
    
    
    
}
