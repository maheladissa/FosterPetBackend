package com.fosterpet.backend.imagemetadata;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {

    ImageMetadata findByImageHash(String imageHash);

}
