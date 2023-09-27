package com.fosterpet.backend.kennel;

import com.fosterpet.backend.user.User;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class KennelServiceImpl implements KennelService {

    @Autowired
    private KennelRepository kennelRepository;

    @Override
    public KennelResponse save(KennelRequest request){
        try {
            User owner = new User();
            owner.setUserId(request.getOwnerId());
            MultipartFile image = request.getImage();
            byte[] imageBytes = image.getBytes();

            Kennel kennel = Kennel.builder()
                    .kennelName(request.getKennelName())
                    .kennelAddress(request.getKennelAddress())
                    .kennelLocation(request.getKennelLocation())
                    .owner(owner)
                    .image(new Binary(imageBytes))
                    .build();

            var saved = kennelRepository.save(kennel);
            byte[] savedBytes = saved.getImage().getData();
            File temporaryFile = File.createTempFile("profile-image", ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(temporaryFile);
            fileOutputStream.write(imageBytes);
            fileOutputStream.close();

            return KennelResponse.builder()
                    .kennelId(saved.getKennelID())
                    .kennelName(saved.getKennelName())
                    .kennelAddress(saved.getKennelAddress())
                    .kennelLocation(saved.getKennelLocation())
                    .ownerId(saved.getOwner().getUserId())
                    .ownerName(saved.getOwner().getFirstName()+saved.getOwner().getLastName())
                    //.ownerPhone(saved.getOwner().getPhoneNumber())
                    .ownerEmail(saved.getOwner().getEmail())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KennelResponse> getKennelStartWith(String name){
        var kennels = kennelRepository.findByKennelNameStartsWith(name);
        List<KennelResponse> kennelResponses = new ArrayList<>();
        for (Kennel kennel : kennels) {
            byte[] imageBytes = kennel.getImage().getData();
            // Save the decoded image to a temporary location on the server.
            File temporaryFile = null;
            try {
                temporaryFile = File.createTempFile("profile-image", ".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(temporaryFile);
                fileOutputStream.write(imageBytes);
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            KennelResponse kennelResponse = KennelResponse.builder()
                    .kennelId(kennel.getKennelID())
                    .kennelName(kennel.getKennelName())
                    .kennelAddress(kennel.getKennelAddress())
                    .kennelLocation(kennel.getKennelLocation())
                    .ownerId(kennel.getOwner().getUserId())
                    //.ownerAddress(kennel.getOwner().getAddress().toString())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
                    .image(temporaryFile.toURI().toString())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }

    @Override
    public List<KennelResponse> getAllKennels(){
        var kennels =  kennelRepository.findAll();
        List<KennelResponse> kennelResponses = new ArrayList<>();
        for (Kennel kennel : kennels) {
            byte[] imageBytes = kennel.getImage().getData();
            // Save the decoded image to a temporary location on the server.
            File temporaryFile = null;
            try {
                temporaryFile = File.createTempFile("profile-image", ".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(temporaryFile);
                fileOutputStream.write(imageBytes);
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            KennelResponse kennelResponse = KennelResponse.builder()
                    .kennelId(kennel.getKennelID())
                    .kennelName(kennel.getKennelName())
                    .kennelAddress(kennel.getKennelAddress())
                    .kennelLocation(kennel.getKennelLocation())
                    .ownerId(kennel.getOwner().getUserId())
                    //.ownerAddress(kennel.getOwner().getAddress().toString())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
                    .image(temporaryFile.toURI().toString())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }

    @Override
    public List<KennelResponse> getKennelsByOwner(String ownerId){
        var kennels =   kennelRepository.findByOwnerUserId(ownerId);
        List<KennelResponse> kennelResponses = new ArrayList<>();
        for (Kennel kennel : kennels) {
            byte[] imageBytes = kennel.getImage().getData();
            // Save the decoded image to a temporary location on the server.
            File temporaryFile = null;
            try {
                temporaryFile = File.createTempFile("profile-image", ".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(temporaryFile);
                fileOutputStream.write(imageBytes);
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            KennelResponse kennelResponse = KennelResponse.builder()
                    .kennelId(kennel.getKennelID())
                    .kennelName(kennel.getKennelName())
                    .kennelAddress(kennel.getKennelAddress())
                    .kennelLocation(kennel.getKennelLocation())
                    .ownerId(kennel.getOwner().getUserId())
                    //.ownerAddress(kennel.getOwner().getAddress().toString())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
                    .image(temporaryFile.toURI().toString())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }
}
