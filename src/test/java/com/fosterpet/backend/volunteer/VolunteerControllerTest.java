package com.fosterpet.backend.volunteer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VolunteerControllerTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveVolunteer_Success() {
        VolunteerRequest request = new VolunteerRequest();
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.save(any(VolunteerRequest.class))).thenReturn(response);

        ResponseEntity<?> result = volunteerController.save(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void saveVolunteer_Failure() {
        VolunteerRequest request = new VolunteerRequest();
        when(volunteerService.save(any(VolunteerRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.save(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllVolunteers_Success() {
        List<VolunteerResponse> responseList = new ArrayList<>();
        when(volunteerService.findAll()).thenReturn(responseList);

        ResponseEntity<?> result = volunteerController.getAllVolunteers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllVolunteers_Failure() {
        when(volunteerService.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getAllVolunteers();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getVolunteerById_Success() {
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.findById(anyString())).thenReturn(response);

        ResponseEntity<?> result = volunteerController.getVolunteerById("volunteerId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getVolunteerById_Failure() {
        when(volunteerService.findById(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getVolunteerById("volunteerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllActiveVolunteers_Success() {
        List<VolunteerResponse> responseList = new ArrayList<>();
        when(volunteerService.getAllActiveVolunteers()).thenReturn(responseList);

        ResponseEntity<?> result = volunteerController.getAllActiveVolunteers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllActiveVolunteers_Failure() {
        when(volunteerService.getAllActiveVolunteers()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getAllActiveVolunteers();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void approveVolunteer_Success() {
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.approveVolunteer(anyString())).thenReturn(response);

        ResponseEntity<?> result = volunteerController.approveVolunteer("volunteerId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void approveVolunteer_Failure() {
        when(volunteerService.approveVolunteer(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.approveVolunteer("volunteerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getVolunteerByUserId_Success() {
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.findByUserId(anyString())).thenReturn(response);

        ResponseEntity<?> result = volunteerController.getVolunteerByUserId("userId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getVolunteerByUserId_Failure() {
        when(volunteerService.findByUserId(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getVolunteerByUserId("userId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void updateVolunteer_Success() {
        VolunteerRequest request = new VolunteerRequest();
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.update(any(VolunteerRequest.class))).thenReturn(response);

        ResponseEntity<?> result = volunteerController.updateVolunteer(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updateVolunteer_Failure() {
        VolunteerRequest request = new VolunteerRequest();
        when(volunteerService.update(any(VolunteerRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.updateVolunteer(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getVolunteersByLocation_Success() {
        List<VolunteerResponse> responseList = new ArrayList<>();
        when(volunteerService.getVolunteersNear(anyDouble(), anyDouble(), anyDouble())).thenReturn(responseList);

        ResponseEntity<?> result = volunteerController.getVolunteersByLocation(1.0, 2.0, 3.0);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getVolunteersByLocation_Failure() {
        when(volunteerService.getVolunteersNear(anyDouble(), anyDouble(), anyDouble())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getVolunteersByLocation(1.0, 2.0, 3.0);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void updateRate_Success() {
        VolunteerPaymentRateRequest request = new VolunteerPaymentRateRequest();
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.updatePaymentRate(any(VolunteerPaymentRateRequest.class))).thenReturn(response);

        ResponseEntity<?> result = volunteerController.updateRate(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updateRate_Failure() {
        VolunteerPaymentRateRequest request = new VolunteerPaymentRateRequest();
        when(volunteerService.updatePaymentRate(any(VolunteerPaymentRateRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.updateRate(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void deleteVolunteer_Success() {
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.deleteVolunteer(anyString())).thenReturn(response);

        ResponseEntity<?> result = volunteerController.deleteVolunteer("volunteerId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deleteVolunteer_Failure() {
        when(volunteerService.deleteVolunteer(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.deleteVolunteer("volunteerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getVolunteersNearAndAnimalType_Success() {
        List<VolunteerResponse> responseList = new ArrayList<>();
        when(volunteerService.filterVolunteers(anyDouble(), anyDouble(), anyDouble(), anyString())).thenReturn(responseList);

        ResponseEntity<?> result = volunteerController.getVolunteersNearAndAnimalType(1.0, 2.0, 3.0, "Dog");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getVolunteersNearAndAnimalType_Failure() {
        when(volunteerService.filterVolunteers(anyDouble(), anyDouble(), anyDouble(), anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getVolunteersNearAndAnimalType(1.0, 2.0, 3.0, "Dog");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void rejectVolunteer_Success() {
        VolunteerResponse response = new VolunteerResponse();
        when(volunteerService.rejectVolunteer(anyString())).thenReturn(response);

        ResponseEntity<?> result = volunteerController.rejectVolunteer("volunteerId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void rejectVolunteer_Failure() {
        when(volunteerService.rejectVolunteer(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.rejectVolunteer("volunteerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getVolunteersByTimePeriod_Success() {
        List<VolunteerResponse> responseList = new ArrayList<>();
        when(volunteerService.getVolunteerByTimePeriod(anyString(), anyString())).thenReturn(responseList);

        ResponseEntity<?> result = volunteerController.getVolunteersByTimePeriod("2024-01-01", "2024-12-31");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getVolunteersByTimePeriod_Failure() {
        when(volunteerService.getVolunteerByTimePeriod(anyString(), anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = volunteerController.getVolunteersByTimePeriod("2024-01-01", "2024-12-31");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }
}