package com.fosterpet.backend.pet;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import com.fosterpet.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private ImageMetadataService imageMetadataService;

    @InjectMocks
    private PetServiceImpl petService;

    private Pet pet;
    private PetRequest petRequest;
    private User owner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        owner = new User();
        owner.setUserId("owner123");
        owner.setFirstName("Jane");
        owner.setLastName("Doe");

        pet = Pet.builder()
                .petID("pet1")
                .owner(owner)
                .petType("Dog")
                .petName("Buddy")
                .petAddress(new Address("123 Main St", "Apt 1", "Cityville", 12345))
                .KASL_regNo("KASL123456")
                .petAge(5)
                .petWeight(20)
                .petBreed("Golden Retriever")
                .petMediConditions("None")
                .petVaccinationStatus("Up to date")
                .petImages(List.of(mock(ImageMetadata.class)))
                .profileImage(mock(ImageMetadata.class))
                .createdAt(Instant.now())
                .isActive(true)
                .build();

        petRequest = new PetRequest();
        petRequest.setOwnerId("owner123");
        petRequest.setPetType("Dog");
        petRequest.setPetName("Buddy");
        petRequest.setPetAddress1("123 Main St");
        petRequest.setPetAddress2("Apt 1");
        petRequest.setPetCity("Cityville");
        petRequest.setPetZip("12345");
        petRequest.setKASL_regNo("KASL123456");
        petRequest.setPetAge(5);
        petRequest.setPetWeight(20);
        petRequest.setPetBreed("Golden Retriever");
        petRequest.setPetMediConditions("None");
        petRequest.setPetVaccinationStatus("Up to date");
        petRequest.setProfileImage(mock(MultipartFile.class));
        petRequest.setPetImages(List.of(mock(MultipartFile.class)));
    }

    @Test
    void testSavePet() throws Exception {
        when(imageMetadataService.save(any(MultipartFile.class))).thenReturn(mock(ImageMetadata.class));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        PetResponse response = petService.save(petRequest);

        assertThat(response).isNotNull();
        assertThat(response.getPetID()).isEqualTo("pet1");
        verify(petRepository, times(1)).save(any(Pet.class));
        verify(imageMetadataService, times(2)).save(any(MultipartFile.class)); // For profileImage and petImages
    }

    @Test
    void testGetAllPets() {
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<PetResponse> responses = petService.getAllPets();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getPetID()).isEqualTo("pet1");
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void testGetPetsByOwner() {
        when(petRepository.findByOwnerUserId("owner123")).thenReturn(List.of(pet));

        List<PetResponse> responses = petService.getPetsByOwner("owner123");

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getPetID()).isEqualTo("pet1");
        verify(petRepository, times(1)).findByOwnerUserId("owner123");
    }

    @Test
    void testGetPetById() {
        when(petRepository.findByPetID("pet1")).thenReturn(pet);

        PetResponse response = petService.getPetById("pet1");

        assertThat(response).isNotNull();
        assertThat(response.getPetID()).isEqualTo("pet1");
        verify(petRepository, times(1)).findByPetID("pet1");
    }

    @Test
    void testCountPetsByTimePeriod() {
        Instant startDate = Instant.now().minusSeconds(3600);
        Instant endDate = Instant.now();

        when(petRepository.countPetsByTimePeriod(startDate, endDate)).thenReturn(1L);

        Long count = petService.countPetsByTimePeriod(startDate, endDate);

        assertThat(count).isEqualTo(1L);
        verify(petRepository, times(1)).countPetsByTimePeriod(startDate, endDate);
    }
}
