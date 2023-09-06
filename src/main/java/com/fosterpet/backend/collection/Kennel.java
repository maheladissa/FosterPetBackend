package com.fosterpet.backend.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "kennel")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Kennel {
    @Id
    private String kennelID;
    private String kennelName;
    private String kennelEmail;
    private Address kennelAddress;


}
