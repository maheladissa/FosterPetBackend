package com.fosterpet.backend.complaint;

import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    @Override
    public List<ComplaintResponse> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        complaintRepository.findAll().forEach(complaints::add);
        return buildComplaintResponses(complaints);
    }

    @Override
    public List<ComplaintResponse> getAllComplaintsByUser(String userId) {
        List<Complaint> complaints = complaintRepository.findByUserUserId(userId);
        return buildComplaintResponses(complaints);
    }

    @Override
    public List<ComplaintResponse> getAllComplaintsByKennel(String kennelId) {
        List<Complaint> complaints = complaintRepository.findByKennelKennelID(kennelId);
        return buildComplaintResponses(complaints);
    }

    @Override
    public ComplaintResponse getComplaintById(String complaintId) {
        var complaint = complaintRepository.findByComplaintId(complaintId);
        return buildComplaintResponse(complaint);
    }

    @Override
    public ComplaintResponse updateComplaintStatus(ComplaintRequest complaintRequest) {
        var complaint = complaintRepository.findByComplaintId(complaintRequest.getComplaintId());
        complaint.setStatus(complaintRequest.getStatus());
        var saved = complaintRepository.save(complaint);
        return buildComplaintResponse(saved);
    }

    @Override
    public ComplaintResponse save(ComplaintRequest complaintRequest) {
        User user = new User();
        user.setUserId(complaintRequest.getUserId());

        Kennel kennel = new Kennel();
        kennel.setKennelID(complaintRequest.getKennelId());

        Booking booking = new Booking();
        booking.setBookingID(complaintRequest.getBookingId());

        Complaint complaint = Complaint.builder()
                .user(user)
                .kennel(kennel)
                .booking(booking)
                .message(complaintRequest.getMessage())
                .status("PENDING")
                .build();
        var saved = complaintRepository.save(complaint);

        return buildComplaintResponse(saved);

    }

    private ComplaintResponse buildComplaintResponse(Complaint complaint) {
        return ComplaintResponse.builder()
                .complaintId(complaint.getComplaintId())
                .message(complaint.getMessage())
                .build();

    }

    private List<ComplaintResponse> buildComplaintResponses(List<Complaint> complaints) {
        List<ComplaintResponse> complaintResponses = new ArrayList<>();
        complaints.forEach(complaint -> complaintResponses.add(buildComplaintResponse(complaint)));
        return complaintResponses;
    }




}
