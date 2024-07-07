package com.fosterpet.backend.payment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String>{
    List<Invoice> findByBookingOwnerUserId(String userId);
    List<Invoice> findByBookingKennelKennelID(String kennelId);
    List<Invoice> findByBookingBookingID(String bookingId);
    List<Invoice> findByBookingVolunteerVolunteerId(String volunteerId);
}
