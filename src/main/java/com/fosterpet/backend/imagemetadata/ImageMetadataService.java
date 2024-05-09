package com.fosterpet.backend.imagemetadata;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageMetadataService {

    ImageMetadata save(MultipartFile image) throws Exception;

    List<ImageMetadata> getAllImageMetadata();

    ImageMetadata getImageMetadataById(String imageMetadataId);

    //ImageMetadata updateImageMetadata(ImageMetadata imageMetadata);

}
