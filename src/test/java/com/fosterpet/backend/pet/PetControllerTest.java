package com.fosterpet.backend.pet;

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

class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePet_Success() {
        PetRequest request = new PetRequest();
        PetResponse response = new PetResponse();
        when(petService.save(any(PetRequest.class))).thenReturn(response);

        ResponseEntity<?> result = petController.save(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void savePet_Failure() {
        PetRequest request = new PetRequest();
        when(petService.save(any(PetRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = petController.save(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getAllPets_Success() {
        List<PetResponse> responseList = new ArrayList<>();
        when(petService.getAllPets()).thenReturn(responseList);

        ResponseEntity<?> result = petController.getAllPets();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getAllPets_Failure() {
        when(petService.getAllPets()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = petController.getAllPets();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getPetsByOwnerId_Success() {
        List<PetResponse> responseList = new ArrayList<>();
        when(petService.getPetsByOwner(anyString())).thenReturn(responseList);

        ResponseEntity<?> result = petController.getPetsByOwnerId("ownerId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void getPetsByOwnerId_Failure() {
        when(petService.getPetsByOwner(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = petController.getPetsByOwnerId("ownerId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void getPetById_Success() {
        PetResponse response = new PetResponse();
        when(petService.getPetById(anyString())).thenReturn(response);

        ResponseEntity<?> result = petController.getPetById("petId");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getPetById_Failure() {
        when(petService.getPetById(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = petController.getPetById("petId");

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void updatePet_Success() {
        PetRequest request = new PetRequest();
        PetResponse response = new PetResponse();
        when(petService.updatePet(any(PetRequest.class))).thenReturn(response);

        ResponseEntity<?> result = petController.updatePet(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updatePet_Failure() {
        PetRequest request = new PetRequest();
        when(petService.updatePet(any(PetRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = petController.updatePet(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }

    @Test
    void deletePet_Failure() {
        String petId = "petId";
        when(petService.deletePet(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> result = petController.deletePet(petId);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error", result.getBody());
    }
}
