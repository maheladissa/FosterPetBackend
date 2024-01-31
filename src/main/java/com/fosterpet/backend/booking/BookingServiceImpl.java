package com.fosterpet.backend.booking;

import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.pet.Pet;
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

        Pet pet = new Pet();
        pet.setPetID(request.getPetID());

        Kennel kennel = new Kennel();
        kennel.setKennelID(request.getKennelID());

        User volunteer = new User();
        volunteer.setUserId(request.getVolunteerID());


        Booking booking = Booking.builder()
                .pet(pet)
                .owner(owner)
                .kennel(kennel)
                .volunteer(volunteer)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        var saved = bookingRepository.save(booking);
        return BookingResponse.builder()
                .bookingID(saved.getBookingID())
                .petID(saved.getPet().getPetID())
                .ownerID(saved.getOwner().getUserId())
                .kennelID(saved.getKennel().getKennelID())
                .volunteerID(saved.getVolunteer().getUserId())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .build();
    }

    private List<BookingResponse> buildBookingResponses(List<Booking> bookings) {
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingResponse bookingResponse = BookingResponse.builder()
                    .petID(booking.getPet().getPetID())
                    .ownerID(booking.getOwner().getUserId())
                    .kennelID(booking.getKennel().getKennelID())
                    .volunteerID(booking.getVolunteer().getUserId())
                    .startDate(booking.getStartDate())
                    .endDate(booking.getEndDate())
                    .build();
            bookingResponses.add(bookingResponse);
        }

        return bookingResponses;
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        var bookings = bookingRepository.findAll();
        return buildBookingResponses(bookings);
    }

    @Override
    public List<BookingResponse> getBookingsByOwner(String ownerId) {
        var bookings = bookingRepository.findByOwnerUserId(ownerId);
        return buildBookingResponses(bookings);
    }
}