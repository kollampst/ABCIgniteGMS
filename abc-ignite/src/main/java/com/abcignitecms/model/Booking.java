package com.abcignitecms.model;

import java.time.LocalDate;
import java.util.Objects;

import com.abcignitecms.exceptions.InvalidDateException;

public class Booking {
	 private Member member;
	    private GymClass gymClass;
	    private LocalDate participationDate;
		public Booking(Member member, GymClass gymClass, LocalDate participationDate) {
			super();
			if (participationDate == null) {
	            throw new IllegalArgumentException("Participation date cannot be null");
	        }
			if (participationDate.isBefore(LocalDate.now())) {
		        throw new InvalidDateException("Participation date cannot be in the past");
		    }
			this.member = member;
			this.gymClass = gymClass;
			this.participationDate = participationDate;
		}
		public Member getMember() {
			return member;
		}
		public void setMember(Member member) {
			this.member = member;
		}
		public GymClass getGymClass() {
			return gymClass;
		}
		public void setGymClass(GymClass gymClass) {
			this.gymClass = gymClass;
		}
		public LocalDate getParticipationDate() {
			return participationDate;
		}
		public void setParticipationDate(LocalDate participationDate) {
			this.participationDate = participationDate;
		}
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Booking booking = (Booking) o;
	        return Objects.equals(member, booking.member) &&
	               Objects.equals(gymClass, booking.gymClass) &&
	               Objects.equals(participationDate, booking.participationDate);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(member, gymClass, participationDate);
	    }
	    
	    

}
