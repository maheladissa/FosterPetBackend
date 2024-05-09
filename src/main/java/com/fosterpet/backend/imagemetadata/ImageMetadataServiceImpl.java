package com.fosterpet.backend.imagemetadata;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import jakarta.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageMetadataServiceImpl implements ImageMetadataService{

    @Autowired
    private ImageMetadataRepository imageMetadataRepository;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${azure.blob-storage.connection-string}")
    private String connectionString;

    private BlobServiceClient blobServiceClient;

    @PostConstruct
    public void init() {
        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    @Override
    public ImageMetadata save(MultipartFile image) throws IOException {
        String imageHash = DigestUtils.sha256Hex(image.getBytes());

        if (imageMetadataRepository.findByImageHash(imageHash) != null) {
            var metadata = imageMetadataRepository.findByImageHash(imageHash);
            return ImageMetadata.builder()
                    .imageID(metadata.getImageID())
                    .imageFileName(metadata.getImageFileName())
                    .imageType(metadata.getImageType())
                    .imageHash(metadata.getImageHash())
                    .imageUrl(metadata.getImageUrl())
                    .build();
        }
        else {
            ImageMetadata imageMetadata = ImageMetadata.builder()
                    .imageFileName(image.getOriginalFilename())
                    .imageType(image.getContentType())
                    .imageHash(imageHash)
                    .build();
            var saved = imageMetadataRepository.save(imageMetadata);

            String fileName = saved.getImageID() + "." + image.getOriginalFilename().split("\\.")[1];
            BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(fileName);
            blobClient.upload(image.getInputStream(), image.getSize(), true);

            imageMetadata = ImageMetadata.builder()
                    .imageID(saved.getImageID())
                    .imageFileName(fileName)
                    .imageType(image.getContentType())
                    .imageHash(imageHash)
                    .imageUrl(blobClient.getBlobUrl())
                    .build();

            saved = imageMetadataRepository.save(imageMetadata);

            return ImageMetadata.builder()
                    .imageID(saved.getImageID())
                    .imageFileName(saved.getImageFileName())
                    .imageType(saved.getImageType())
                    .imageHash(saved.getImageHash())
                    .imageUrl(saved.getImageUrl())
                    .build();
        }

    }

    @Override
    public List<ImageMetadata> getAllImageMetadata() {
        return imageMetadataRepository.findAll();
    }

    @Override
    public ImageMetadata getImageMetadataById(String imageMetadataId) {
        return imageMetadataRepository.findById(imageMetadataId).orElse(null);
    }


}
