package com.fosterpet.backend.admin;

import com.fosterpet.backend.admin.dashboard.DashboardResponse;
import com.fosterpet.backend.booking.BookingRepository;
import com.fosterpet.backend.complaint.ComplaintRepository;
import com.fosterpet.backend.kennel.KennelRepository;
import com.fosterpet.backend.pet.PetRepository;
import com.fosterpet.backend.review.ReviewRepository;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.volunteer.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    KennelRepository kennelRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Override
    public DashboardResponse getDashboardData() {

        Integer activeUsers = userRepository.countUserByIsAccountActive(true);

        Integer activeAgents = volunteerRepository.countVolunteerByIsActive(true) + kennelRepository.countKennelByIsActive(true);

        Integer ongoingFostering = bookingRepository.countAllByStatus("ONGOING");

        Integer completedFostering = bookingRepository.countAllByStatus("COMPLETED");


        return DashboardResponse.builder()
                .activeUsers(activeUsers)
                .activeAgents(activeAgents)
                .ongoingFostering(ongoingFostering)
                .completedFostering(completedFostering)
                .build();
    }
}
