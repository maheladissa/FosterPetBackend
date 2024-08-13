package com.fosterpet.backend.pet;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PetTest {
    private Pet pet;
    private final String petID = "testPetId";
    private final User owner = mock(User.class);
    private final String petType = "Dog";
    private final String petName = "Buddy";
    private final Address petAddress = mock(Address.class);
    private final String KASL_regNo = "KASL123456";
    private final Integer petAge = 5;
    private final Integer petWeight = 20;
    private final String petBreed = "Golden Retriever";
    private final String petMediConditions = "None";
    private final String petVaccinationStatus = "Up to date";
    private final List<ImageMetadata> petImages = mock(List.class);
    private final ImageMetadata profileImage = mock(ImageMetadata.class);
    private final Instant createdAt = Instant.now();
    private final Boolean isActive = true;

    @BeforeEach
    void setUp() {
        pet = Pet.builder()
                .petID(petID)
                .owner(owner)
                .petType(petType)
                .petName(petName)
                .petAddress(petAddress)
                .KASL_regNo(KASL_regNo)
                .petAge(petAge)
                .petWeight(petWeight)
                .petBreed(petBreed)
                .petMediConditions(petMediConditions)
                .petVaccinationStatus(petVaccinationStatus)
                .petImages(petImages)
                .profileImage(profileImage)
                .createdAt(createdAt)
                .isActive(isActive)
                .build();
    }

    @Test
    void testPetDetails() {
        assertEquals(petID, pet.getPetID());
        assertEquals(owner, pet.getOwner());
        assertEquals(petType, pet.getPetType());
        assertEquals(petName, pet.getPetName());
        assertEquals(petAddress, pet.getPetAddress());
        assertEquals(KASL_regNo, pet.getKASL_regNo());
        assertEquals(petAge, pet.getPetAge());
        assertEquals(petWeight, pet.getPetWeight());
        assertEquals(petBreed, pet.getPetBreed());
        assertEquals(petMediConditions, pet.getPetMediConditions());
        assertEquals(petVaccinationStatus, pet.getPetVaccinationStatus());
        assertEquals(petImages, pet.getPetImages());
        assertEquals(profileImage, pet.getProfileImage());
        assertEquals(createdAt, pet.getCreatedAt());
        assertEquals(isActive, pet.getIsActive());
    }
}
