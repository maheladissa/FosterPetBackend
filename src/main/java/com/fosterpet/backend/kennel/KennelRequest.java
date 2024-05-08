package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KennelRequest {
    private String kennelName;
    private String kennelAddress1;
    private String kennelAddress2;
    private String kennelCity;
    private String kennelZip;
    private Double kennelLongitude;
    private Double kennelLatitude;
    private String ownerId;
    private MultipartFile image;
}