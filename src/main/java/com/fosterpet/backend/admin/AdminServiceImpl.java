package com.fosterpet.backend.admin;

import com.fosterpet.backend.admin.dashboard.DashboardResponse;
import com.fosterpet.backend.admin.periodFilter.PeriodFilterResponse;
import com.fosterpet.backend.booking.BookingRepository;
import com.fosterpet.backend.complaint.ComplaintRepository;
import com.fosterpet.backend.kennel.KennelRepository;
import com.fosterpet.backend.payment.InvoiceRepository;
import com.fosterpet.backend.pet.PetRepository;
import com.fosterpet.backend.review.ReviewRepository;
import com.fosterpet.backend.user.UserRepository;
import com.fosterpet.backend.volunteer.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public DashboardResponse getDashboardData() {

        Integer activeUsers = userRepository.countUserByIsAccountActive(true);

        Integer activeAgents = volunteerRepository.countVolunteerByIsActive(true) + kennelRepository.countKennelByIsActive(true);

        Integer ongoingFostering = bookingRepository.countAllByStatus("ONGOING");

        Integer completedFostering = bookingRepository.countAllByStatus("COMPLETED");

        Integer pendingFostering = bookingRepository.countAllByStatus("PENDING");

        Integer canceledFostering = bookingRepository.countAllByStatus("CANCELED") + bookingRepository.countAllByStatus("REJECTED");

        Integer activeKennels = kennelRepository.countKennelByIsActive(true);

        Integer activeVolunteers = volunteerRepository.countVolunteerByIsActive(true);

        List<Integer> payment = Arrays.asList(100, 200, 150, 200, 150, 50, 300);

        return DashboardResponse.builder()
                .activeUsers(activeUsers)
                .activeAgents(activeAgents)
                .totalKennels(activeKennels)
                .totalVolunteers(activeVolunteers)
                .ongoingFostering(ongoingFostering)
                .weeklyPayment(payment)
                .completedFostering(completedFostering)
                .canceledFostering(canceledFostering)
                .pendingFostering(pendingFostering)
                .build();
    }

    @Override
    public PeriodFilterResponse getPeriodFilterData(String startDate, String endDate) {

        Long userCount = userRepository.countUsersByTimePeriod(Instant.parse(startDate), Instant.parse(endDate));

        Long petCount = petRepository.countPetsByTimePeriod(Instant.parse(startDate), Instant.parse(endDate));

        Long kennelCount = kennelRepository.countKennelsByTimePeriod(Instant.parse(startDate), Instant.parse(endDate));

        Long bookingCount = bookingRepository.countBookingsByStartDate(Date.from(Instant.parse(startDate)), Date.from(Instant.parse(endDate)));

        return PeriodFilterResponse.builder()
                .userCount(userCount)
                .petCount(petCount)
                .kennelCount(kennelCount)
                .bookingCount(bookingCount)
                .build();
    }

    @Override
    public Double getDailyRevenue(String date) {
        Instant start = Instant.parse(date);
        Instant end = start.plusSeconds(86400);
        Double sum = invoiceRepository.findByCreatedAtBetween(start, end).stream().mapToDouble(invoice -> invoice.getAmount()).sum();
        return sum*0.15;
    }
}
