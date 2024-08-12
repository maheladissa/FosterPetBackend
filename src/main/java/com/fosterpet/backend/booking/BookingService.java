package com.fosterpet.backend.booking;

import java.util.Date;
import java.util.List;

public interface BookingService {
    BookingResponse save(BookingRequest request);

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getBookingsByOwner(String ownerId);

    BookingResponse getBookingById(String bookingId);

    List<BookingResponse> getBookingsByKennel(String kennelId);

    List<BookingResponse> getBookingsByVolunteer(String kennelId);

    List<BookingResponse> getBookingsByPet(String petId);

    BookingResponse updateBooking(BookingRequest request);

    BookingResponse confirmBooking(String bookingId);

    BookingResponse cancelBooking(String bookingId);

    BookingResponse completeBooking(String bookingId);

    BookingResponse rejectBooking(String bookingId);

    BookingResponse ongoingBooking(String bookingId);

    Integer countOngoingBookings();

    Long countBookingsByStartDate(Date startDate, Date endDate);

    List<BookingResponse> getBookingsByStatus(String status);



}
