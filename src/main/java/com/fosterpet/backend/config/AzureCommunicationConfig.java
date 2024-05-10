package com.fosterpet.backend.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AzureCommunicationConfig {

    private final String endpoint;
    private final String connectionString; // Or access key if not using connection string

    public AzureCommunicationConfig(String endpoint, String connectionString) {
        this.endpoint = endpoint;
        this.connectionString = connectionString;
    }
}