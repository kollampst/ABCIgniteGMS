package com.abcignitecms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingResponseDTO {
	private String memberName;
    private String className;
    private LocalTime startTime;
    private LocalDate participationDate;

    // Constructor
    public BookingResponseDTO(String memberName, String className, LocalTime startTime, LocalDate participationDate) {
        this.memberName = memberName;
        this.className = className;
        this.startTime = startTime;
        this.participationDate = participationDate;
    }

    // Getters and Setters
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getParticipationDate() {
        return participationDate;
    }

    public void setParticipationDate(LocalDate participationDate) {
        this.participationDate = participationDate;
    }
}
