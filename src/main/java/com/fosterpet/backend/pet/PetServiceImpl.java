package com.fosterpet.backend.pet;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.ImageToBase64Converter;
import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public PetResponse save(PetRequest request) {
        try {
            User owner = new User();
            owner.setUserId(request.getOwnerId());
            MultipartFile image = request.getPetImage();
            InputStream inputStream = image.getInputStream();

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
                    .petImage("image/"+ image.getOriginalFilename() +";base64,"+ ImageToBase64Converter.convert(inputStream))
                    .build();
            var saved = petRepository.save(pet);
            return PetResponse.builder()
                    .petID(saved.getPetID())
                    .petType(saved.getPetType())
                    .petName(saved.getPetName())
                    .petAddress(saved.getPetAddress())
                    .KASL_regNo(saved.getKASL_regNo())
                    .petAge(saved.getPetAge())
                    .petWeight(saved.getPetWeight())
                    .petBreed(saved.getPetBreed())
                    .petMediConditions(saved.getPetMediConditions())
                    .petVaccinationStatus(saved.getPetVaccinationStatus())
                    .ownerId(saved.getOwner().getUserId())
                    .petImage(saved.getPetImage())
                    .build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PetResponse> getAllPets() {
        var pets = petRepository.findAll();
        return createPetResponsesFromPet(pets);
    }

    @Override
    public List<PetResponse> getPetsByOwner(String ownerId) {
        var pets = petRepository.findByOwnerUserId(ownerId);
        return createPetResponsesFromPet(pets);
    }

    private List<PetResponse> createPetResponsesFromPet(List<Pet> pets) {
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponse petResponse = PetResponse.builder()
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
                    .build();

            petResponses.add(petResponse);
        }

        return petResponses;
    }

    @Override
    public PetResponse getPetById(String petId) {
        var pet = petRepository.findByPetID(petId);
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
                .build();
    }

    @Override
    public PetResponse updatePet(PetRequest request){
        try {
            User owner = new User();
            owner.setUserId(request.getOwnerId());
            MultipartFile image = request.getPetImage();
            InputStream inputStream = image.getInputStream();

            Pet pet = Pet.builder()
                    .petID(request.getPetID())
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
                    .petImage("image/"+ image.getOriginalFilename() +";base64,"+ ImageToBase64Converter.convert(inputStream))
                    .build();
            var saved = petRepository.save(pet);
            return PetResponse.builder()
                    .petID(saved.getPetID())
                    .petType(saved.getPetType())
                    .petName(saved.getPetName())
                    .petAddress(saved.getPetAddress())
                    .KASL_regNo(saved.getKASL_regNo())
                    .petAge(saved.getPetAge())
                    .petWeight(saved.getPetWeight())
                    .petBreed(saved.getPetBreed())
                    .petMediConditions(saved.getPetMediConditions())
                    .petVaccinationStatus(saved.getPetVaccinationStatus())
                    .ownerId(saved.getOwner().getUserId())
                    .petImage(saved.getPetImage())
                    .build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
