package com.abcignitecms.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.abcignitecms.model.Booking;
import com.abcignitecms.model.GymClass;

@Repository
public class BookingRepository {

    private final List<Booking> bookings = new ArrayList<>();

    public void save(Booking booking) {
        bookings.add(booking);
    }

    public long countBookings(GymClass gymClass) {
        return bookings.stream()
            .filter(b -> b.getGymClass().equals(gymClass))
            .count();
    }

    // New method to check if a member has already booked a class on a given date
    public List<Booking> findBookingsByMemberAndClassAndDate(String member, String className, LocalDate participationDate) {
        return bookings.stream()
            .filter(booking -> booking.getMember().getName().equalsIgnoreCase(member))
            .filter(booking -> booking.getGymClass().getName().equalsIgnoreCase(className))
            .filter(booking -> booking.getParticipationDate().equals(participationDate))
            .collect(Collectors.toList());
    }

    
//    public List<Booking> findBookings(String member, LocalDate startDate, LocalDate endDate) {
//        return bookings.stream()
//        	.filter(booking -> (member == null || booking.getMember().getName().equalsIgnoreCase(member)))
//            .filter(booking -> (startDate == null || !booking.getParticipationDate().isBefore(startDate)))
//            .filter(booking -> (endDate == null || !booking.getParticipationDate().isAfter(endDate)))
//            .collect(Collectors.toList());
//    }
    
    public List<Booking> findBookings(String member, LocalDate startDate, LocalDate endDate) {
        return bookings.stream()
            .filter(booking -> {
                boolean matchesMember = (member == null || booking.getMember().getName().equalsIgnoreCase(member));
                boolean matchesStartDate = (startDate == null || !booking.getParticipationDate().isBefore(startDate));
                boolean matchesEndDate = (endDate == null || !booking.getParticipationDate().isAfter(endDate));

//                System.out.println("Booking: " + booking.getMember().getName() +
//                                   ", Participation Date: " + booking.getParticipationDate() +
//                                   ", Matches Member: " + matchesMember +
//                                   ", Matches Start Date: " + matchesStartDate +
//                                   ", Matches End Date: " + matchesEndDate);
                
                return matchesMember && matchesStartDate && matchesEndDate;
            })
            .collect(Collectors.toList());
    }
    
    // You can also have a method that checks for duplicate bookings specifically
    public boolean isBookingExist(String member, GymClass gymClass, LocalDate participationDate) {
        return bookings.stream()
            .anyMatch(booking -> booking.getMember().equals(member) && booking.getGymClass().equals(gymClass)
                    && booking.getParticipationDate().equals(participationDate));
    }

}
