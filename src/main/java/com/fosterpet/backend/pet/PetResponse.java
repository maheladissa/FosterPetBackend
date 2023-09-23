package com.fosterpet.backend.pet;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {
    private String petID;
    private String petType;
    private String petName;
    private Address petAddress;
    private Location petLocation;
    private String KASL_regNo;
    private int petAge;
    private int petWeight;
    private String petBreed;
    private String petMediConditions;
    private String petVaccinationStatus;
    private String ownerId;
}
