package com.fosterpet.backend.booking;

import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public BookingResponse save(BookingRequest request) {
        User owner = new User();
        owner.setUserId(request.getOwnerID());
        Booking booking = Booking.builder()
                .petID(request.getPetID())
                .ownerID(request.getOwnerID())
                .kennelID(request.getKennelID())
                .volunteerID(request.getVolunteerID())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        var saved = bookingRepository.save(booking);
        return BookingResponse.builder()
                .bookingID(saved.getBookingID())
                .petID(saved.getPetID())
                .ownerID(saved.getOwnerID())
                .kennelID(saved.getKennelID())
                .volunteerID(saved.getVolunteerID())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .build();
    }
    @Override
    public List<BookingResponse> getAllBookings() {
        var bookings = bookingRepository.findAll();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingResponse bookingResponse = BookingResponse.builder()
                .petID(booking.getPetID())
                .ownerID(booking.getOwnerID())
                .kennelID(booking.getKennelID())
                .volunteerID(booking.getVolunteerID())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
            bookingResponses.add(bookingResponse);
        }

        return bookingResponses;
    }

    @Override
    public List<BookingResponse> getBookingsByOwner(String ownerId) {
        var bookings = bookingRepository.findByOwnerUserId(ownerId);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingResponse bookingResponse = BookingResponse.builder()
                .petID(booking.getPetID())
                .ownerID(booking.getOwnerID())
                .kennelID(booking.getKennelID())
                .volunteerID(booking.getVolunteerID())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
            bookingResponses.add(bookingResponse);
        }

        return bookingResponses;
    }
}