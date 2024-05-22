package com.fosterpet.backend.volunteer;

import java.util.List;

public interface VolunteerService {

    VolunteerResponse save(VolunteerRequest volunteerRequest);
    VolunteerResponse update(VolunteerRequest volunteerRequest);
    List<VolunteerResponse> findAll();
    VolunteerResponse findById(String volunteerId);
    VolunteerResponse findByUserId(String userId);
    List<VolunteerResponse> getVolunteersNear(double longitude, double latitude, double maxDistance);
}
