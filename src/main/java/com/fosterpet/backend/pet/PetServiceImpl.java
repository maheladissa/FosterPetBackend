package com.fosterpet.backend.pet;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ImageMetadataService imageMetadataService;

    @Override
    public PetResponse save(PetRequest request) {
        try {
            User owner = new User();
            owner.setUserId(request.getOwnerId());
            ArrayList<ImageMetadata> images = new ArrayList<>();
            for (MultipartFile image : request.getPetImages()) {
                ImageMetadata imageMetadata = imageMetadataService.save(image);
                images.add(imageMetadata);
            }

            Pet pet = Pet.builder()
                    .petType(request.getPetType())
                    .petName(request.getPetName())
                    .petAddress(Address.builder()
                            .address1(request.getPetAddress1())
                            .address2(request.getPetAddress2())
                            .city(request.getPetCity())
                            .zipCode(Integer.parseInt(request.getPetZip()))
                            .build())
                    .KASL_regNo(request.getKASL_regNo())
                    .petAge(request.getPetAge())
                    .petWeight(request.getPetWeight())
                    .petBreed(request.getPetBreed())
                    .petMediConditions(request.getPetMediConditions())
                    .petVaccinationStatus(request.getPetVaccinationStatus())
                    .owner(owner)
                    .petImages(images)
                    .createdAt(Instant.now())
                    .build();

            var saved = petRepository.save(pet);

            return petResponseBuilder(saved);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PetResponse> getAllPets() {
        var pets = petRepository.findAll();
        return createPetResponsesFromPets(pets);
    }

    @Override
    public List<PetResponse> getPetsByOwner(String ownerId) {
        var pets = petRepository.findByOwnerUserId(ownerId);
        return createPetResponsesFromPets(pets);
    }


    @Override
    public PetResponse getPetById(String petId) {
        var pet = petRepository.findByPetID(petId);
        return petResponseBuilder(pet);
    }

    @Override
    public PetResponse updatePet(PetRequest request){
        try {
            var pet = petRepository.findByPetID(request.getPetID());

            if (request.getPetImages() != null) {
                ArrayList<ImageMetadata> images = new ArrayList<>();
                for (MultipartFile image : request.getPetImages()) {
                    ImageMetadata imageMetadata = imageMetadataService.save(image);
                    images.add(imageMetadata);
                }
                pet.setPetImages(images);
            }
            if(request.getPetType() != null) pet.setPetType(request.getPetType());
            if(request.getPetName() != null) pet.setPetName(request.getPetName());
            if(request.getPetAddress1() != null && request.getPetAddress2() != null && request.getPetCity() != null && request.getPetZip() != null) {
                pet.setPetAddress(Address.builder()
                        .address1(request.getPetAddress1())
                        .address2(request.getPetAddress2())
                        .city(request.getPetCity())
                        .zipCode(Integer.parseInt(request.getPetZip()))
                        .build());
            }
            if(request.getKASL_regNo() != null) pet.setKASL_regNo(request.getKASL_regNo());
            if(request.getPetAge() != null) pet.setPetAge(request.getPetAge());
            if(request.getPetWeight() != null) pet.setPetWeight(request.getPetWeight());
            if(request.getPetBreed() != null) pet.setPetBreed(request.getPetBreed());
            if(request.getPetMediConditions() != null) pet.setPetMediConditions(request.getPetMediConditions());
            if(request.getPetVaccinationStatus() != null) pet.setPetVaccinationStatus(request.getPetVaccinationStatus());


            var saved = petRepository.save(pet);

            return petResponseBuilder(saved);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PetResponse deletePet(String petId){
        Pet pet = petRepository.findByPetID(petId);
        pet.setIsActive(false);
        var saved = petRepository.save(pet);
        return petResponseBuilder(saved);
    }

    private List<PetResponse> createPetResponsesFromPets(List<Pet> pets) {
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = petResponseBuilder(pet);

            petResponses.add(petResponse);
        }

        return petResponses;
    }

    private PetResponse petResponseBuilder(Pet pet){
        return PetResponse.builder()
                .petID(pet.getPetID())
                .petType(pet.getPetType())
                .petName(pet.getPetName())
                .petAddress(pet.getPetAddress())
                .KASL_regNo(pet.getKASL_regNo())
                .petAge(pet.getPetAge())
                .petWeight(pet.getPetWeight())
                .petBreed(pet.getPetBreed())
                .petMediConditions(pet.getPetMediConditions())
                .petVaccinationStatus(pet.getPetVaccinationStatus())
                .ownerId(pet.getOwner().getUserId())
                .ownerName(pet.getOwner().getFirstName()+" "+pet.getOwner().getLastName())
                .ownerEmail(pet.getOwner().getEmail())
                .createdAt(pet.getCreatedAt())
                .petImages(new ArrayList<>() {{
                    for (ImageMetadata image : pet.getPetImages()) {
                        add(image.getImageUrl());
                    }
                }})
                .build();
    }
}
