package com.fosterpet.backend.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "pet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pet {
    @Id
    private String petID;
    private String petType;
    private String petName;
    private String KASL_regNo;
    private int petAge;
    private int petWeight;
    private String petBreed;
    private String petMediConditions;
    private String petVaccinationStatus;

}
