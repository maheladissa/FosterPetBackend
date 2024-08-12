package com.fosterpet.backend.booking;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository <Booking, String> {

    List<Booking> findByOwnerUserId(String ownerId);
    List<Booking> findAll();
    Booking findByBookingID(String bookingId);
    List<Booking> findByKennelKennelID(String kennelId);
    List<Booking> findByVolunteerVolunteerId(String volunteerId);
    List<Booking> findByPetPetID(String petId);

    Integer countAllByStatus(String ongoing);

    @Query(value = "{ 'startDate': { $gte: ?0, $lte: ?1 } }", count = true)
    Long countBookingsByStartDate(Date startDate, Date endDate);

    List<Booking> getBookingByStatus(String status);
}
