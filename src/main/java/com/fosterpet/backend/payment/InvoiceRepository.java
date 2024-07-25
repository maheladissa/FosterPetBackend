package com.fosterpet.backend.payment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String>{
    List<Invoice> findByBookingOwnerUserId(String userId);
    List<Invoice> findByBookingKennelKennelID(String kennelId);
    Invoice findByBookingBookingID(String bookingId);
    List<Invoice> findByBookingVolunteerVolunteerId(String volunteerId);

    @Query("{'paymentDate': {$gte: ?0, $lt: ?1}}")
    List<Invoice> findByCreatedAtBetween(Instant start, Instant end);

}
