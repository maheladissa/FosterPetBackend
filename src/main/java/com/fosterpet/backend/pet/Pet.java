package com.fosterpet.backend.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
@Data
@Builder
@Document(collection = "pet")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    private String petID;
    @DBRef
    private User owner;
    private String petType;
    private String petName;
    private Address petAddress;
    private String KASL_regNo;
    private Integer petAge;
    private Integer petWeight;
    private String petBreed;
    private String petMediConditions;
    private String petVaccinationStatus;
    private String petImage;

}
