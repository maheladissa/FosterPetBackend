package com.fosterpet.backend.kennel;

import java.util.List;

public interface KennelService {
    KennelResponse save(KennelRequest request);

    List<KennelResponse> getKennelStartWith(String name);

    List<KennelResponse> getAllKennels();

    List<KennelResponse> getKennelsByOwner(String ownerId);

    List<KennelResponse> getKennelsNear(double longitude, double latitude, double maxDistance);

}
