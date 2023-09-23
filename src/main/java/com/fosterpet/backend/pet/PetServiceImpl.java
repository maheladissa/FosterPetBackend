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
    public PetResponse save(PetRequest request){
        User owner = new User();
        owner.setUserId(request.getOwnerId());
        Pet pet = Pet.builder()
                .petName(request.getPetName())
                .petAddress(request.getPetAddress())
                .petLocation(request.getPetLocation())
                .owner(owner)
                .build();
        var saved = petRepository.save(pet);
        return PetResponse.builder()
                .petID(saved.getPetID())
                .petName(saved.getPetName())
                .petAddress(saved.getPetAddress())
                .petLocation(saved.getPetLocation())
                .ownerId(saved.getOwner().getUserId())
                .ownerAddress(saved.getOwner().getAddress().toString())
                .ownerName(saved.getOwner().getFirstName()+saved.getOwner().getLastName())
                .ownerPhone(saved.getOwner().getPhoneNumber())
                .ownerEmail(saved.getOwner().getEmail())
                .build();
    }

    @Override
    public List<PetResponse> getPetStartWith(String name){
        var pets = petRepository.findByPetNameStartsWith(name);
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = PetResponse.builder()
                    .petID(pet.getPetID())
                    .petName(pet.getPetName())
                    .petAddress(pet.getPetAddress())
                    .petLocation(pet.getPetLocation())
                    .ownerId(pet.getOwner().getUserId())
                    .ownerAddress(pet.getOwner().getAddress().toString())
                    .ownerName(pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName())
                    .ownerPhone(pet.getOwner().getPhoneNumber())
                    .ownerEmail(pet.getOwner().getEmail())
                    .build();

            petResponses.add(petResponse);
        }

        return petResponses;
    }

    @Override
    public List<PetResponse> getAllPets(){
        var pets =  petRepository.findAll();
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = PetResponse.builder()
                    .petID(pet.getPetID())
                    .petName(pet.getPetName())
                    .petAddress(pet.getPetAddress())
                    .petLocation(pet.getPetLocation())
                    .ownerId(pet.getOwner().getUserId())
                    .ownerAddress(pet.getOwner().getAddress().toString())
                    .ownerName(pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName())
                    .ownerPhone(pet.getOwner().getPhoneNumber())
                    .ownerEmail(pet.getOwner().getEmail())
                    .build();

            petResponses.add(petResponse);
        }

        return petResponses;
    }

    @Override
    public List<PetResponse> getPetsByOwner(String ownerId){
        var pets =   petRepository.findByOwnerUserId(ownerId);
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = PetResponse.builder()
                    .petID(pet.getPetID())
                    .petName(pet.getPetName())
                    .petAddress(pet.getPetAddress())
                    .petLocation(pet.getPetLocation())
                    .ownerId(pet.getOwner().getUserId())
                    .ownerAddress(pet.getOwner().getAddress().toString())
                    .ownerName(pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName())
                    .ownerPhone(pet.getOwner().getPhoneNumber())
                    .ownerEmail(pet.getOwner().getEmail())
                    .build();

            petResponses.add(petResponse);
        }

        return petResponses;
    }
}
