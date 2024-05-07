package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KennelResponse {
    private String kennelId;
    private String kennelName;
    private Address kennelAddress;
    private Location kennelLocation;

    private String ownerId;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
    private String image;
}
