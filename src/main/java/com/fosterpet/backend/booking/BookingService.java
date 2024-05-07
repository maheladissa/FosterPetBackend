package com.fosterpet.backend.booking;

import java.util.List;

public interface BookingService {
    BookingResponse save(BookingRequest request);

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getBookingsByOwner(String ownerId);
}
