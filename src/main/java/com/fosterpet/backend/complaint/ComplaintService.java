package com.fosterpet.backend.complaint;

import java.util.List;

public interface ComplaintService {
    ComplaintResponse save(ComplaintRequest reviewRequest);

    List<ComplaintResponse> getAllComplaints();

    List<ComplaintResponse> getAllComplaintsByUser(String userId);

    List<ComplaintResponse> getAllComplaintsByKennel(String kennelId);

    ComplaintResponse getComplaintById(String complaintId);

    ComplaintResponse updateComplaintStatus(ComplaintRequest complaintRequest);


}
