package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.kennel.KennelPaymentRateRequest;

import java.util.List;

public interface VolunteerService {

    VolunteerResponse save(VolunteerRequest volunteerRequest);
    VolunteerResponse update(VolunteerRequest volunteerRequest);
    List<VolunteerResponse> findAll();
    VolunteerResponse findById(String volunteerId);
    VolunteerResponse findByUserId(String userId);
    List<VolunteerResponse> getVolunteersNear(double longitude, double latitude, double maxDistance);
    List<VolunteerResponse> filterVolunteers(double longitude, double latitude, double maxDistance, String animalType);
    VolunteerResponse updatePaymentRate(VolunteerPaymentRateRequest request);

    VolunteerResponse deleteVolunteer(String volunteerId);

    List<VolunteerResponse> getAllActiveVolunteers();

    VolunteerResponse approveVolunteer(String volunteerId);

    VolunteerResponse rejectVolunteer(String volunteerId);

    Long countVolunteersByTimePeriod(String startDate, String endDate);

    List<VolunteerResponse> getVolunteerByTimePeriod(String startDate, String endDate);
}
