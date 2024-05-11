package com.fosterpet.backend.volunteer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends MongoRepository <Volunteer,String> {
    List<Volunteer> findByUserUserId(String userId);
    List<Volunteer> findAll();
    Volunteer findByVolunteerId(String volunteerId);
}
