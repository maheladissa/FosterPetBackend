package com.fosterpet.backend.booking;

import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.kennel.KennelRepository;
import com.fosterpet.backend.pet.Pet;
import com.fosterpet.backend.pet.PetRepository;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public BookingResponse save(BookingRequest request) {
        User owner = new User();
        owner.setUserId(petRepository.findByPetID(request.getPetID()).getOwner().getUserId());

        Pet pet = new Pet();
        pet.setPetID(request.getPetID());

        Kennel kennel = new Kennel();
        kennel.setKennelID(request.getKennelID());

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(request.getVolunteerID());


        Booking booking = Booking.builder()
                .pet(pet)
                .owner(owner)
                .kennel(request.getKennelID() != null ? kennel : null)
                .volunteer(request.getVolunteerID() != null ? volunteer : null)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .rate(request.getKennelID() != null ? kennelRepository.findByKennelID(request.getKennelID()).getRate(petRepository.findByPetID(request.getPetID()).getPetType()) : 0)
                .status("PENDING")
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
    public List<BookingResponse> getBookingsByVolunteer(String volunteerId) {
        var bookings = bookingRepository.findByVolunteerVolunteerId(volunteerId);
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

    @Override
    public BookingResponse confirmBooking(String bookingId) {
        var booking = bookingRepository.findByBookingID(bookingId);
        if ("PENDING".equals(booking.getStatus())){
            booking.setStatus("CONFIRMED");
        }
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
    }

    @Override
    public BookingResponse cancelBooking(String bookingId) {
        var booking = bookingRepository.findByBookingID(bookingId);
        if ("PENDING".equals(booking.getStatus())){booking.setStatus("CANCELLED");}
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
    }

    @Override
    public BookingResponse completeBooking(String bookingId) {
        var booking = bookingRepository.findByBookingID(bookingId);
        if ("ONGOING".equals(booking.getStatus())){booking.setStatus("COMPLETED");}
        booking.setTotal(booking.getRate() * (booking.getEndDate().getTime() - booking.getStartDate().getTime()) / (1000 * 60 * 60));
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
    }

    @Override
    public BookingResponse rejectBooking(String bookingId) {
        var booking = bookingRepository.findByBookingID(bookingId);
        if ("PENDING".equals(booking.getStatus())){booking.setStatus("REJECTED");}
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
    }

    @Override
    public BookingResponse ongoingBooking(String bookingId) {
        var booking = bookingRepository.findByBookingID(bookingId);
        if ("CONFIRMED".equals(booking.getStatus())){booking.setStatus("ONGOING");}
        var saved = bookingRepository.save(booking);
        return buildBookingResponse(saved);
    }


    private BookingResponse buildBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingID(booking.getBookingID())
                .petID(booking.getPet().getPetID())
                .ownerID(booking.getOwner().getUserId())
                .kennelID(booking.getKennel() != null ? booking.getKennel().getKennelID() : null)
                .volunteerID(booking.getVolunteer() != null ? booking.getVolunteer().getVolunteerId() : null)
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .rate(booking.getRate())
                .total(booking.getTotal())
                .status(booking.getStatus())
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