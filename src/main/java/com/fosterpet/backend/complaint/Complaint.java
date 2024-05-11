package com.fosterpet.backend.complaint;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "complaint")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {
    @Id
    private String complaintId;
    @DBRef
    private User user;
    @DBRef
    private Kennel kennel;
    @DBRef
    private Booking booking;

    private String message;
    private String status;

    private String adminID;
    private String remarks;


}
