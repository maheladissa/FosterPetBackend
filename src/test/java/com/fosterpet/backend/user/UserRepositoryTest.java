package com.fosterpet.backend.user;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clear all previous data

        testUser = User.builder()
                .userId(new ObjectId().toString())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .isAccountActive(true)
                .createdAt(Instant.now())
                .build();

        userRepository.save(testUser);
    }

    @Test
    void testFindByFirstNameStartsWith() {
        List<User> users = userRepository.findByFirstNameStartsWith("Jo");
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getFirstName()).startsWith("Jo");
    }

    @Test
    void testFindByFirstName() {
        Optional<User> user = userRepository.findByFirstName("John");
        assertThat(user).isPresent();
        assertThat(user.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindByEmail() {
        Optional<User> user = userRepository.findByEmail("john.doe@example.com");
        assertThat(user).isPresent();
        assertThat(user.get().getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindByUserId() {
        User user = userRepository.findByUserId(testUser.getUserId());
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testCountUserByIsAccountActive() {
        Integer count = userRepository.countUserByIsAccountActive(true);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testCountUsersByTimePeriod() {
        Instant now = Instant.now();
        Instant oneDayAgo = now.minusSeconds(86400); // 24 hours ago

        Long count = userRepository.countUsersByTimePeriod(oneDayAgo, now);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testFindUsersByTimePeriod() {
        Instant now = Instant.now();
        Instant oneDayAgo = now.minusSeconds(86400); // 24 hours ago

        List<User> users = userRepository.findUsersByTimePeriod(oneDayAgo, now);
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getEmail()).isEqualTo("john.doe@example.com");
    }
}
