package com.fosterpet.backend.kennel;

import com.fosterpet.backend.user.User;

import java.util.List;

public interface KennelService {
    KennelResponse save(KennelRequest request);

    List<KennelResponse> getKennelStartWith(String name);

    List<KennelResponse> getAllKennels();

    List<KennelResponse> getKennelsByOwner(String ownerId);
}
