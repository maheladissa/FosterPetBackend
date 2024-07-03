package com.fosterpet.backend.booking;


import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.pet.Pet;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.volunteer.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String bookingID;
    private Pet petID;
    private User ownerID;
    private Kennel kennelID;
    private Volunteer volunteerID;
    private Date startDate;
    private Date endDate;
    private Double rate;
    private Double total;
    private String status;
}
