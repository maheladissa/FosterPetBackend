package com.fosterpet.backend.kennel;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KennelRequest {
    private String kennelId;
    private String kennelName;
    private String kennelAddress1;
    private String kennelAddress2;
    private String kennelCity;
    private String kennelZip;
    private Double kennelLongitude;
    private Double kennelLatitude;
    private String ownerId;
    private List<MultipartFile> images;
    private MultipartFile profileImage;
}