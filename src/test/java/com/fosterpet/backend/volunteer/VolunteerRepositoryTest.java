package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.common.Location;


import com.fosterpet.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class VolunteerRepositoryTest {

    @Autowired
    private VolunteerRepository volunteerRepository;

    private Volunteer volunteer1;
    private Volunteer volunteer2;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        volunteerRepository.deleteAll();

        user1 = new User();
        user1.setUserId("user123");
        user2 = new User();
        user2.setUserId("user456");

        Location location = new Location();
        location.setType("Point");
        location.setCoordinates(new double[]{80.1, 6.1});

        volunteer1 = Volunteer.builder()
                .volunteerId("volunteer1")
                .user(user1)
                .isActive(true)
                .isApproved(true)
                .volunteerLocation(location)
                .createdDate(Instant.now().minusSeconds(3600))
                .build();

        volunteer2 = Volunteer.builder()
                .volunteerId("volunteer2")
                .user(user2)
                .isActive(false)
                .isApproved(false)
                .volunteerLocation(location)
                .createdDate(Instant.now())
                .build();

        volunteerRepository.save(volunteer1);
        volunteerRepository.save(volunteer2);
    }

    @Test
    public void testFindByUserUserId() {
        List<Volunteer> result = volunteerRepository.findByUserUserId("user123");
        assertThat(result).hasSize(1).contains(volunteer1);
    }

    @Test
    public void testFindAll() {
        List<Volunteer> result = volunteerRepository.findAll();
        assertThat(result).hasSize(2).contains(volunteer1, volunteer2);
    }

    @Test
    public void testFindByVolunteerId() {
        Volunteer result = volunteerRepository.findByVolunteerId("volunteer1");
        assertThat(result).isEqualTo(volunteer1);
    }

    @Test
    public void testFindByLocationNear() {
        // Assuming you have set volunteerLocation properly in the setup
        // Add appropriate location details in volunteer1 and volunteer2
    }

    @Test
    public void testCountVolunteerByIsActive() {
        Integer count = volunteerRepository.countVolunteerByIsActive(true);
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void testFindByLocationNearAndIsActiveAndAnimalType() {
        // Assuming you have set location and animalType properly in the setup
        // Add appropriate location and animalType details in volunteer1 and volunteer2
    }

    @Test
    public void testFindByIsApprovedAndAndIsActive() {
        List<Volunteer> result = volunteerRepository.findByIsApprovedAndAndIsActive(true, true);
        assertThat(result).hasSize(1).contains(volunteer1);
    }

    @Test
    public void testGetVolunteerByTimePeriod() {
        Instant startDate = Instant.now().minusSeconds(7200);
        Instant endDate = Instant.now();

        List<Volunteer> result = volunteerRepository.getVolunteerByTimePeriod(startDate, endDate);
        assertThat(result).hasSize(2).contains(volunteer1, volunteer2);
    }
}
