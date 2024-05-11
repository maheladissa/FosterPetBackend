package com.fosterpet.backend.complaint;

import java.util.List;

public interface ComplaintService {
    ComplaintResponse save(ComplaintRequest reviewRequest);

    List<ComplaintResponse> getAllComplaints();

    List<ComplaintResponse> getAllComplaintsByUser(String userId);

    List<ComplaintResponse> getAllComplaintsByKennel(String kennelId);

    List<ComplaintResponse> getAllComplaintsByVolunteer(String volunteerId);

    List<ComplaintResponse> getAllComplaintsByBooking(String bookingId);

    List<ComplaintResponse> getAllComplaintsByStatus(String status);

    ComplaintResponse getComplaintById(String complaintId);

    ComplaintResponse updateComplaintStatus(ComplaintRequest complaintRequest);

    ComplaintResponse updateComplaintAdmin(ComplaintRequest complaintRequest);

    ComplaintResponse updateComplaintRemarks(ComplaintRequest complaintRequest);




}
