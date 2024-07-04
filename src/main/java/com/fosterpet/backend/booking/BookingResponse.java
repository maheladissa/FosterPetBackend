package com.fosterpet.backend.booking;


import com.fosterpet.backend.kennel.Kennel;
import com.fosterpet.backend.kennel.KennelResponse;
import com.fosterpet.backend.pet.Pet;
import com.fosterpet.backend.pet.PetResponse;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserResponse;
import com.fosterpet.backend.volunteer.Volunteer;
import com.fosterpet.backend.volunteer.VolunteerResponse;
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
    private PetResponse pet;
    private UserResponse owner;
    private KennelResponse kennel;
    private VolunteerResponse volunteer;
    private Date startDate;
    private Date endDate;
    private Double rate;
    private Double total;
    private String status;
}
