package com.fosterpet.backend.kennel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.common.PaymentRates;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "kennel")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Kennel {
    @Id
    private String kennelID;
    @DBRef
    private User owner;
    private String kennelName;
    private Address kennelAddress;
    private Location kennelLocation;
    @DBRef
    private List<ImageMetadata> images;
    private List<PaymentRates> paymentRates;
    private Boolean isActive;

    public Double getRate(String animalType) {
        return paymentRates.stream()
                .filter(paymentRate -> paymentRate.getAnimalType().equals(animalType))
                .findFirst()
                .map(PaymentRates::getRate)
                .orElse(null);
    }
}
