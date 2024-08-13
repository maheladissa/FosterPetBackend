package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.common.Location;
import com.fosterpet.backend.common.PaymentRates;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class VolunteerTest {
    private Volunteer volunteer;
    private final String volunteerId = "testVolunteerId";
    private final User user = mock(User.class);
    private final Location volunteerLocation = mock(Location.class);
    private final String nicNumber = "1234567890";
    private final List<ImageMetadata> images = mock(List.class);
    private final ImageMetadata profileImage = mock(ImageMetadata.class);
    private final List<PaymentRates> paymentRates = mock(List.class);
    private final Boolean isActive = true;
    private final Boolean isApproved = true;

    @BeforeEach
    void setUp() {
        volunteer = Volunteer.builder()
                .volunteerId(volunteerId)
                .user(user)
                .volunteerLocation(volunteerLocation)
                .nicNumber(nicNumber)
                .images(images)
                .profileImage(profileImage)
                .paymentRates(paymentRates)
                .isActive(isActive)
                .isApproved(isApproved)
                .build();
    }

    @Test
    void testVolunteerDetails() {
        assertEquals(volunteerId, volunteer.getVolunteerId());
        assertEquals(user, volunteer.getUser());
        assertEquals(volunteerLocation, volunteer.getVolunteerLocation());
        assertEquals(nicNumber, volunteer.getNicNumber());
        assertEquals(images, volunteer.getImages());
        assertEquals(profileImage, volunteer.getProfileImage());
        assertEquals(paymentRates, volunteer.getPaymentRates());
        assertEquals(isActive, volunteer.getIsActive());
        assertEquals(isApproved, volunteer.getIsApproved());
    }

}