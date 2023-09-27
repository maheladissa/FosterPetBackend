package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KennelRequest {
    private String kennelName;
    private Address kennelAddress;
    private Location kennelLocation;
    private String ownerId;
    private MultipartFile image;
}
