package com.fosterpet.backend.complaint;

import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public List<ComplaintResponse> getAllComplaintsByVolunteer(String volunteerId) {
        List<Complaint> complaints = complaintRepository.findByVolunteerVolunteerId(volunteerId);
        return buildComplaintResponses(complaints);
    }

    @Override
    public ComplaintResponse getComplaintById(String complaintId) {
        var complaint = complaintRepository.findByComplaintId(complaintId);
        return buildComplaintResponse(complaint);
    }

    @Override
    public List<ComplaintResponse> getAllComplaintsByBooking(String bookingId) {
        List<Complaint> complaints = complaintRepository.findByBookingBookingID(bookingId);
        return buildComplaintResponses(complaints);
    }

    @Override
    public List<ComplaintResponse> getAllComplaintsByStatus(String status) {
        List<Complaint> complaints = complaintRepository.findByStatus(status);
        return buildComplaintResponses(complaints);
    }

    @Override
    public ComplaintResponse updateComplaintStatus(ComplaintRequest complaintRequest) {
        var complaint = complaintRepository.findByComplaintId(complaintRequest.getComplaintId());
        complaint.setStatus(complaintRequest.getStatus());
        var saved = complaintRepository.save(complaint);
        return buildComplaintResponse(saved);
    }

    @Override
    public ComplaintResponse updateComplaintAdmin(ComplaintRequest complaintRequest) {
        var complaint = complaintRepository.findByComplaintId(complaintRequest.getComplaintId());
        complaint.setAdminID(complaintRequest.getAdminID());
        var saved = complaintRepository.save(complaint);
        return buildComplaintResponse(saved);
    }

    @Override
    public ComplaintResponse updateComplaintRemarks(ComplaintRequest complaintRequest) {
        var complaint = complaintRepository.findByComplaintId(complaintRequest.getComplaintId());
        complaint.setRemarks(complaintRequest.getRemarks());
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

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(complaintRequest.getVolunteerId());

        Complaint complaint = Complaint.builder()
                .user(user)
                .kennel(complaintRequest.getKennelId() != null ? kennel : null)
                .volunteer(complaintRequest.getVolunteerId() != null ? volunteer : null)
                .booking(booking)
                .message(complaintRequest.getMessage())
                .status("PENDING")
                .createdAt(Instant.now())
                .build();
        var saved = complaintRepository.save(complaint);

        return buildComplaintResponse(saved);

    }

    private ComplaintResponse buildComplaintResponse(Complaint complaint) {
        return ComplaintResponse.builder()
                .complaintId(complaint.getComplaintId())
                .bookingId(complaint.getBooking().getBookingID())
                .kennelId(complaint.getKennel() != null ? complaint.getKennel().getKennelID() : null)
                .kennelName(complaint.getKennel() != null ? complaint.getKennel().getKennelName() : null)
                .volunteerId(complaint.getVolunteer() != null ? complaint.getVolunteer().getVolunteerId() : null)
                .volunteerName(complaint.getVolunteer() != null ? complaint.getVolunteer().getUser().getFirstName()+complaint.getVolunteer().getUser().getLastName() : null)
                .userId(complaint.getUser().getUserId())
                .userName(complaint.getUser().getFirstName()+complaint.getUser().getLastName())
                .status(complaint.getStatus())
                .message(complaint.getMessage())
                .createdAt(complaint.getCreatedAt())
                .build();

    }

    private List<ComplaintResponse> buildComplaintResponses(List<Complaint> complaints) {
        List<ComplaintResponse> complaintResponses = new ArrayList<>();
        complaints.forEach(complaint -> complaintResponses.add(buildComplaintResponse(complaint)));
        return complaintResponses;
    }




}
