package com.fosterpet.backend.volunteer;

import com.fosterpet.backend.common.Location;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VolunteerServiceImplTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private ImageMetadataService imageMetadataService;

    @InjectMocks
    private VolunteerServiceImpl volunteerService;

    private Volunteer volunteer;
    private VolunteerRequest volunteerRequest;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId("user123");
        user.setFirstName("John");
        user.setLastName("Doe");

        volunteer = Volunteer.builder()
                .volunteerId("volunteer1")
                .user(user)
                .volunteerLocation(new Location("Point", new double[]{45.0, -93.0}))
                .nicNumber("12345")
                .isActive(true)
                .isApproved(false)
                .build();

        volunteerRequest = new VolunteerRequest();
        volunteerRequest.setUserId("user123");
        volunteerRequest.setNicNumber("12345");
        volunteerRequest.setVolunteerLongitude(45.0);
        volunteerRequest.setVolunteerLatitude(-93.0);
    }

    @Test
    void testSaveVolunteer() throws Exception {
        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setImageUrl("profileImageUrl");

        when(imageMetadataService.save(any(MultipartFile.class))).thenReturn(imageMetadata);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        VolunteerResponse response = volunteerService.save(volunteerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getVolunteerId()).isEqualTo("volunteer1");
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
        verify(imageMetadataService, times(1)).save(any(MultipartFile.class));
    }

    @Test
    void testUpdateVolunteer() {
        when(volunteerRepository.findByVolunteerId("volunteer1")).thenReturn(volunteer);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        VolunteerResponse response = volunteerService.update(volunteerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getVolunteerId()).isEqualTo("volunteer1");
        verify(volunteerRepository, times(1)).findByVolunteerId("volunteer1");
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testFindAllVolunteers() {
        when(volunteerRepository.findAll()).thenReturn(List.of(volunteer));

        List<VolunteerResponse> responses = volunteerService.findAll();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getVolunteerId()).isEqualTo("volunteer1");
        verify(volunteerRepository, times(1)).findAll();
    }

    @Test
    void testFindVolunteerById() {
        when(volunteerRepository.findByVolunteerId("volunteer1")).thenReturn(volunteer);

        VolunteerResponse response = volunteerService.findById("volunteer1");

        assertThat(response).isNotNull();
        assertThat(response.getVolunteerId()).isEqualTo("volunteer1");
        verify(volunteerRepository, times(1)).findByVolunteerId("volunteer1");
    }

    @Test
    void testFindVolunteerByUserId() {
        when(volunteerRepository.findByUserUserId("user123")).thenReturn(List.of(volunteer));

        VolunteerResponse response = volunteerService.findByUserId("user123");

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo("user123");
        verify(volunteerRepository, times(1)).findByUserUserId("user123");
    }

    @Test
    void testGetVolunteersNear() {
        when(volunteerRepository.findByLocationNear(45.0, -93.0, 1000.0)).thenReturn(List.of(volunteer));

        List<VolunteerResponse> responses = volunteerService.getVolunteersNear(45.0, -93.0, 1000.0);

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getVolunteerId()).isEqualTo("volunteer1");
        verify(volunteerRepository, times(1)).findByLocationNear(45.0, -93.0, 1000.0);
    }

    @Test
    void testApproveVolunteer() {
        when(volunteerRepository.findByVolunteerId("volunteer1")).thenReturn(volunteer);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        VolunteerResponse response = volunteerService.approveVolunteer("volunteer1");

        assertThat(response).isNotNull();
        assertThat(response.getIsApproved()).isTrue();
        verify(volunteerRepository, times(1)).findByVolunteerId("volunteer1");
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testRejectVolunteer() {
        when(volunteerRepository.findByVolunteerId("volunteer1")).thenReturn(volunteer);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        VolunteerResponse response = volunteerService.rejectVolunteer("volunteer1");

        assertThat(response).isNotNull();
        assertThat(response.getIsApproved()).isFalse();
        verify(volunteerRepository, times(1)).findByVolunteerId("volunteer1");
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testDeleteVolunteer() {
        when(volunteerRepository.findByVolunteerId("volunteer1")).thenReturn(volunteer);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        VolunteerResponse response = volunteerService.deleteVolunteer("volunteer1");

        assertThat(response).isNotNull();
        assertThat(response.getIsActive()).isFalse();
        verify(volunteerRepository, times(1)).findByVolunteerId("volunteer1");
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testCountVolunteersByTimePeriod() {
        Instant startDate = Instant.now().minusSeconds(3600);
        Instant endDate = Instant.now();

        when(volunteerRepository.getVolunteerByTimePeriod(startDate, endDate)).thenReturn(List.of(volunteer));

        Long count = volunteerService.countVolunteersByTimePeriod(startDate.toString(), endDate.toString());

        assertThat(count).isEqualTo(1L);
        verify(volunteerRepository, times(1)).getVolunteerByTimePeriod(startDate, endDate);
    }

    @Test
    void testGetVolunteerByTimePeriod() {
        Instant startDate = Instant.now().minusSeconds(3600);
        Instant endDate = Instant.now();

        when(volunteerRepository.getVolunteerByTimePeriod(startDate, endDate)).thenReturn(List.of(volunteer));

        List<VolunteerResponse> responses = volunteerService.getVolunteerByTimePeriod(startDate.toString(), endDate.toString());

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getVolunteerId()).isEqualTo("volunteer1");
        verify(volunteerRepository, times(1)).getVolunteerByTimePeriod(startDate, endDate);
    }
}
