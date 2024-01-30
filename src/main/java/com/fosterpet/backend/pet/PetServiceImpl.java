package com.fosterpet.backend.pet;

import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public PetResponse save(PetRequest request) {
        User owner = new User();
        owner.setUserId(request.getOwnerId());
        Pet pet = Pet.builder()
                .petType(request.getPetType())
                .petName(request.getPetName())
                .petAddress(request.getPetAddress())
                .petLocation(request.getPetLocation())
                .KASL_regNo(request.getKASL_regNo())
                .petAge(request.getPetAge())
                .petWeight(request.getPetWeight())
                .petBreed(request.getPetBreed())
                .petMediConditions(request.getPetMediConditions())
                .petVaccinationStatus(request.getPetVaccinationStatus())
                .owner(owner)
                .build();
        var saved = petRepository.save(pet);
        return PetResponse.builder()
                .petID(saved.getPetID())
                .petType(saved.getPetType())
                .petName(saved.getPetName())
                .petAddress(saved.getPetAddress())
                .petLocation(saved.getPetLocation())
                .KASL_regNo(saved.getKASL_regNo())
                .petAge(saved.getPetAge())
                .petWeight(saved.getPetWeight())
                .petBreed(saved.getPetBreed())
                .petMediConditions(saved.getPetMediConditions())
                .petVaccinationStatus(saved.getPetVaccinationStatus())
                .ownerId(saved.getOwner().getUserId())
                .build();
    }

    @Override
    public List<PetResponse> getAllPets() {
        var pets = petRepository.findAll();
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = PetResponse.builder()
                    .petID(pet.getPetID())
                    .petType(pet.getPetType())
                    .petName(pet.getPetName())
                    .petAddress(pet.getPetAddress())
                    .petLocation(pet.getPetLocation())
                    .KASL_regNo(pet.getKASL_regNo())
                    .petAge(pet.getPetAge())
                    .petWeight(pet.getPetWeight())
                    .petBreed(pet.getPetBreed())
                    .petMediConditions(pet.getPetMediConditions())
                    .petVaccinationStatus(pet.getPetVaccinationStatus())
                    .ownerId(pet.getOwner().getUserId())
                    .build();

            petResponses.add(petResponse);
        }

        return petResponses;
    }

    @Override
    public List<PetResponse> getPetsByOwner(String ownerId) {
        var pets = petRepository.findByOwnerUserId(ownerId);
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = PetResponse.builder()
                    .petID(pet.getPetID())
                    .petType(pet.getPetType())
                    .petName(pet.getPetName())
                    .petAddress(pet.getPetAddress())
                    .petLocation(pet.getPetLocation())
                    .KASL_regNo(pet.getKASL_regNo())
                    .petAge(pet.getPetAge())
                    .petWeight(pet.getPetWeight())
                    .petBreed(pet.getPetBreed())
                    .petMediConditions(pet.getPetMediConditions())
                    .petVaccinationStatus(pet.getPetVaccinationStatus())
                    .ownerId(pet.getOwner().getUserId())
                    .build();

            petResponses.add(petResponse);
        }

        return petResponses;
    }
}
