package com.fosterpet.backend.imagemetadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "image_metadata")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ImageMetadata {
    @Id
    private String imageID;
    private String imageType;
    private String imageFileName;
    private String imageHash;
    private String imageUrl;
}
