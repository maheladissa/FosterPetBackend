package com.fosterpet.backend.kennel;

import com.fosterpet.backend.common.Address;
import com.fosterpet.backend.common.Location;
import com.fosterpet.backend.common.PaymentRates;
import com.fosterpet.backend.imagemetadata.ImageMetadata;
import com.fosterpet.backend.imagemetadata.ImageMetadataService;
import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class KennelServiceImpl implements KennelService {

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private ImageMetadataService imageMetadataService;

    @Override
    public KennelResponse save(KennelRequest request){
        try {
            User owner = new User();
            owner.setUserId(request.getOwnerId());
            ArrayList<ImageMetadata> images = new ArrayList<>();
            for (MultipartFile image : request.getImages()) {
                ImageMetadata imageMetadata = imageMetadataService.save(image);
                images.add(imageMetadata);
            }

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
                    .images(images)
                    .isActive(true)
                    .build();

            var saved = kennelRepository.save(kennel);

            return kennelResponseBuilder(saved);
        } catch (Exception e) {
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
    public KennelResponse update(KennelRequest request){
        try {
            var kennel = kennelRepository.findByKennelID(request.getKennelId());

            if (request.getImages() != null) {
                ArrayList<ImageMetadata> images = new ArrayList<>();
                for (MultipartFile image : request.getImages()) {
                    ImageMetadata imageMetadata = imageMetadataService.save(image);
                    images.add(imageMetadata);
                }
                kennel.setImages(images);
            }

            if (request.getKennelName() != null) {
                kennel.setKennelName(request.getKennelName());
            }
            if (request.getKennelAddress1() != null) {
                kennel.setKennelAddress(Address.builder()
                        .address1(request.getKennelAddress1())
                        .address2(request.getKennelAddress2())
                        .city(request.getKennelCity())
                        .zipCode(Integer.parseInt(request.getKennelZip()))
                        .build());
            }
            if (request.getKennelLongitude() != 0) {
                kennel.setKennelLocation(Location.builder()
                        .type("Point")
                        .coordinates(new double[]{request.getKennelLongitude(), request.getKennelLatitude()})
                        .build());
            }

            var saved = kennelRepository.save(kennel);

            return kennelResponseBuilder(saved);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KennelResponse getKennelById(String kennelId){
        var kennel = kennelRepository.findByKennelID(kennelId);
        return kennelResponseBuilder(kennel);
    }

    @Override
    public List<KennelResponse> updatePaymentRate(KennelPaymentRateRequest request){
        List<PaymentRates> paymentRates = new ArrayList<>();
        var kennel = kennelRepository.findByKennelID(request.getKennelId());
        for (PaymentRates paymentRate : request.getPaymentRates()) {
            paymentRates.add(paymentRate);
        }

        kennel.setPaymentRates(paymentRates);

        var saved = kennelRepository.save(kennel);

        return createKennelResponsesFromKennels(List.of(saved));
    }


    private List<KennelResponse> createKennelResponsesFromKennels(List<Kennel> kennels) {
        List<KennelResponse> kennelResponses = new ArrayList<>();
        for (Kennel kennel : kennels) {
            KennelResponse kennelResponse = kennelResponseBuilder(kennel);

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }

    private KennelResponse kennelResponseBuilder (Kennel kennel){
        return KennelResponse.builder()
                .kennelId(kennel.getKennelID())
                .kennelName(kennel.getKennelName())
                .kennelAddress(kennel.getKennelAddress())
                .kennelLocation(kennel.getKennelLocation())
                .ownerId(kennel.getOwner().getUserId())
                .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                .ownerPhone(kennel.getOwner().getPhoneNumber())
                .ownerEmail(kennel.getOwner().getEmail())
                .images(new ArrayList<>() {{
                    for (ImageMetadata image : kennel.getImages()) {
                        add(image.getImageUrl());
                    }
                }})
                .paymentRates(kennel.getPaymentRates())
                .build();
    }
}
