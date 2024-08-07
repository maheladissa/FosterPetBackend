package com.fosterpet.backend.volunteer;


import com.fosterpet.backend.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerRequest {
    private String volunteerId;
    private String userId;
    private String nicNumber;
    private List<MultipartFile> images;
    private MultipartFile profileImage;
    private Double volunteerLongitude;
    private Double volunteerLatitude;

}
