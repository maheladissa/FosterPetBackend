package com.fosterpet.backend.kennel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "kennel")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Kennel {
    @Id
    private String kennelID;
    @DBRef
    private User owner;
    private String kennelName;
    private Address kennelAddress;
    private Location kennelLocation;
    private Binary image;

}
