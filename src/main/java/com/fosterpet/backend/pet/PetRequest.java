package com.fosterpet.backend.pet;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {
    private String petID;
    private String petType;
    private String petName;
    private String petAddress1;
    private String petAddress2;
    private String petCity;
    private String petZip;
    private String KASL_regNo;
    private Integer petAge;
    private Integer petWeight;
    private String petBreed;
    private String petMediConditions;
    private String petVaccinationStatus;
    private String ownerId;
    private List<MultipartFile> petImages;
}
