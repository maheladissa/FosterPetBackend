package com.fosterpet.backend.booking;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    @DBRef
    private String petID;
    private String ownerID;
    private String kennelID;
    private String volunteerID;
    private Date startDate;
    private Date endDate;
}