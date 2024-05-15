package com.fosterpet.backend.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String bookingID;
    private String petID;
    private String kennelID;
    private String volunteerID;
    private Date startDate;
    private Date endDate;
}