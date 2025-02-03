package com.abcignitecms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookingDTO {

    @NotBlank(message = "Member name cannot be empty")
    private String memberName;

    @NotBlank(message = "Class name cannot be empty")
    private String className;

    @NotNull(message = "Participation date is required")
    @FutureOrPresent(message = "Participation date must be in the future")
    private LocalDate participationDate;
    

	public BookingDTO(@NotBlank(message = "Member name cannot be empty") String memberName,
			@NotBlank(message = "Class name cannot be empty") String className,
			@NotNull(message = "Participation date is required") @FutureOrPresent(message = "Participation date must be in the future") LocalDate participationDate) {
		super();
		this.memberName = memberName;
		this.className = className;
		this.participationDate = participationDate;
	}

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

	public LocalDate getParticipationDate() {
		return participationDate;
	}

	public void setParticipationDate(LocalDate participationDate) {
		this.participationDate = participationDate;
	}
    
    

}
