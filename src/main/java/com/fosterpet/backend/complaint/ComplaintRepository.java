package com.fosterpet.backend.complaint;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint, String>{
    List<Complaint> findByKennelKennelID(String kennelId);
    List<Complaint> findByUserUserId(String ownerId);
    List<Complaint> findByBookingBookingID(String bookingId);
    List<Complaint> findByStatus(String status);
    Complaint findByComplaintId(String complaintId);

}
