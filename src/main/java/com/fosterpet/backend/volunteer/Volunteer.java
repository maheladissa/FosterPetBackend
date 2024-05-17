package com.fosterpet.backend.volunteer;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.common.Location;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.user.User;
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
@Document(collection = "volunteer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Volunteer {
    @Id
    private String volunteerId;
    @DBRef
    private User user;
    private Location volunteerLocation;
    private String nicNumber;
    @DBRef
    private List<ImageMetadata> images;
}
