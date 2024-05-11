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

//        User volunteer = new User();
//        volunteer.setUserId(request.getVolunteerID());


        Booking booking = Booking.builder()
                .pet(pet)
                .owner(owner)
                .kennel(kennel)
//                .volunteer(volunteer)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
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

    @Override
    public BookingResponse getBookingById(String bookingId) {
        var booking = bookingRepository.findByBookingID(bookingId);
        return buildBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByKennel(String kennelId) {
        var bookings = bookingRepository.findByKennelKennelID(kennelId);
        return buildBookingResponses(bookings);
    }

    @Override
    public List<BookingResponse> getBookingsByPet(String petId) {
        var bookings = bookingRepository.findByPetPetID(petId);
        return buildBookingResponses(bookings);
    }

    @Override
    public BookingResponse updateBooking(BookingRequest request) {
        var booking = bookingRepository.findByBookingID(request.getBookingID());
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
    }


    private BookingResponse buildBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingID(booking.getBookingID())
                .petID(booking.getPet().getPetID())
                .ownerID(booking.getOwner().getUserId())
                .kennelID(booking.getKennel().getKennelID())
//                .volunteerID(booking.getVolunteer().getUserId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    private List<BookingResponse> buildBookingResponses(List<Booking> bookings) {
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingResponse bookingResponse = buildBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }

        return bookingResponses;
    }
}