package com.fosterpet.backend.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.pet.Pet;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Builder
@Document(collection = "booking")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    private String bookingID;
    @DBRef
    private Pet pet;
    @DBRef
    private User owner;
    @DBRef
    private Kennel kennel;
    @DBRef
    private Volunteer volunteer;


    private Date startDate;
    private Date endDate;
    private Double rate;
    private Double total;
    private String status;
    private String paymentIntentId;

}
