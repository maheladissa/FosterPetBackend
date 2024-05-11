package com.fosterpet.backend.booking;

import java.util.List;

public interface BookingService {
    BookingResponse save(BookingRequest request);

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getBookingsByOwner(String ownerId);

    BookingResponse getBookingById(String bookingId);

    List<BookingResponse> getBookingsByKennel(String kennelId);

    List<BookingResponse> getBookingsByPet(String petId);

    BookingResponse updateBooking(BookingRequest request);
}
