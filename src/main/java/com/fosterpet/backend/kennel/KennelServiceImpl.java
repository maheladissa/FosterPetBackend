package com.fosterpet.backend.kennel;

import com.fosterpet.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KennelServiceImpl implements KennelService {

    @Autowired
    private KennelRepository kennelRepository;

    @Override
    public KennelResponse save(KennelRequest request){
        User owner = new User();
        owner.setUserId(request.getOwnerId());
        Kennel kennel = Kennel.builder()
                .kennelName(request.getKennelName())
                .kennelAddress(request.getKennelAddress())
                .kennelLocation(request.getKennelLocation())
                .owner(owner)
                .build();
        var saved = kennelRepository.save(kennel);
        return KennelResponse.builder()
                .kennelId(saved.getKennelID())
                .kennelName(saved.getKennelName())
                .kennelAddress(saved.getKennelAddress())
                .kennelLocation(saved.getKennelLocation())
                .ownerId(saved.getOwner().getUserId())
                .ownerAddress(saved.getOwner().getAddress().toString())
                .ownerName(saved.getOwner().getFirstName()+saved.getOwner().getLastName())
                .ownerPhone(saved.getOwner().getPhoneNumber())
                .ownerEmail(saved.getOwner().getEmail())
                .build();
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
                    .ownerAddress(kennel.getOwner().getAddress().toString())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
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
                    .ownerAddress(kennel.getOwner().getAddress().toString())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
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
                    .ownerAddress(kennel.getOwner().getAddress().toString())
                    .ownerName(kennel.getOwner().getFirstName() + " " + kennel.getOwner().getLastName())
                    .ownerPhone(kennel.getOwner().getPhoneNumber())
                    .ownerEmail(kennel.getOwner().getEmail())
                    .build();

            kennelResponses.add(kennelResponse);
        }

        return kennelResponses;
    }
}
