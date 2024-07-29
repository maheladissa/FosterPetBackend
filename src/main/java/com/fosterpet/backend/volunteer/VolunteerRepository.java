package com.fosterpet.backend.volunteer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends MongoRepository <Volunteer,String> {
    List<Volunteer> findByUserUserId(String userId);
    List<Volunteer> findAll();
    Volunteer findByVolunteerId(String volunteerId);

    @Query("{ 'volunteerLocation' : { $near : { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } } }")
    List<Volunteer> findByLocationNear(double longitude, double latitude, double maxDistance);

    Integer countVolunteerByIsActive(Boolean isActive);

    @Query("{ 'volunteerLocation' : { $near : { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } }, 'isActive': true, 'isApproved': true, 'paymentRates.animalType': ?3 }")
    List<Volunteer> findByLocationNearAndIsActiveAndAnimalType(double longitude, double latitude, double maxDistance, String animalType);

}
