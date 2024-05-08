package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.ImageToBase64Converter;
import com.fosterpet.backend.common.Location;
import com.fosterpet.backend.user.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
                    .kennelAddress(Address.builder()
                            .address1(request.getKennelAddress1())
                            .address2(request.getKennelAddress2())
                            .city(request.getKennelCity())
                            .zipCode(Integer.parseInt(request.getKennelZip()))
                            .build())
                    .kennelLocation(Location.builder()
                            .type("Point")
                            .coordinates(new double[]{request.getKennelLongitude(), request.getKennelLatitude()})
                            .build())
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
                    .image(saved.getImage().toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KennelResponse> getKennelStartWith(String name){
        var kennels = kennelRepository.findByKennelNameStartsWith(name);
        return createKennelResponsesFromKennels(kennels);
    }

    @Override
    public List<KennelResponse> getAllKennels(){
        var kennels =  kennelRepository.findAll();
        return createKennelResponsesFromKennels(kennels);
    }

    @Override
    public List<KennelResponse> getKennelsByOwner(String ownerId){
        var kennels =   kennelRepository.findByOwnerUserId(ownerId);
        return createKennelResponsesFromKennels(kennels);
    }

    @Override
    public List<KennelResponse> getKennelsNear(double longitude, double latitude, double maxDistance){
        var kennels = kennelRepository.findByLocationNear(longitude, latitude, maxDistance);
        return createKennelResponsesFromKennels(kennels);
    }

    @Override
    public KennelResponse update(String kennelId, KennelRequest request){
        return null;
    }


    private List<KennelResponse> createKennelResponsesFromKennels(List<Kennel> kennels) {
        List<KennelResponse> kennelResponses = new ArrayList<>();
        for (Kennel kennel : kennels) {
            KennelResponse kennelResponse = KennelResponse.builder()
                    .kennelId(kennel.getKennelID())
                    .kennelName(kennel.getKennelName())
                    .kennelAddress(kennel.getKennelAddress())
                    .kennelLocation(kennel.getKennelLocation())
                    .ownerId(kennel.getOwner().getUserId())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
                    .image(kennel.getImage().toString())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }
}
