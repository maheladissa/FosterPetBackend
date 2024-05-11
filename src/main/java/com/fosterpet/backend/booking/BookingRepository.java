package com.fosterpet.backend.booking;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository <Booking, String> {

    List<Booking> findByOwnerUserId(String ownerId);
    List<Booking> findAll();
    Booking findByBookingID(String bookingId);
    List<Booking> findByKennelKennelID(String kennelId);
    List<Booking> findByPetPetID(String petId);

}
