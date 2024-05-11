package com.fosterpet.backend.volunteer;


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
}
