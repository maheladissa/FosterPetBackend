package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.ImageToBase64Converter;
import com.fosterpet.backend.user.User;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            InputStream inputStream = image.getInputStream();

            Kennel kennel = Kennel.builder()
                    .kennelName(request.getKennelName())
                    .kennelAddress(request.getKennelAddress())
                    .kennelLocation(request.getKennelLocation())
                    .owner(owner)
                    .image("image/"+ FilenameUtils.getExtension(image.getOriginalFilename()) +";base64,"+ImageToBase64Converter.convert(inputStream))
                    .build();

            var saved = kennelRepository.save(kennel);
            return KennelResponse.builder()
                    .kennelId(saved.getKennelID())
                    .kennelName(saved.getKennelName())
                    .kennelAddress(saved.getKennelAddress())
                    .kennelLocation(saved.getKennelLocation())
                    .ownerId(saved.getOwner().getUserId())
                    .ownerName(saved.getOwner().getFirstName()+saved.getOwner().getLastName())
                    //.ownerPhone(saved.getOwner().getPhoneNumber())
                    .ownerEmail(saved.getOwner().getEmail())
                    .image(saved.getImage().toString())
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
                    .image(kennel.getImage().toString())
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
                    .image(kennel.getImage().toString())
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
                    .image(kennel.getImage())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }

    @Override
    public List<KennelResponse> getKennelsNear(double longitude, double latitude, double maxDistance){
        var kennels = kennelRepository.findByLocationNear(longitude, latitude, maxDistance);
        List<KennelResponse> kennelResponses = new ArrayList<>();
        for (Kennel kennel : kennels) {
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
                    .image(kennel.getImage())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }
}
