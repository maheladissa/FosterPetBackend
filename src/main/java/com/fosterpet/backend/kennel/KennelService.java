package com.fosterpet.backend.kennel;

import com.fosterpet.backend.user.User;

import java.util.List;

public interface KennelService {
    String save (Kennel kennel);

    List<Kennel> getKennelStartWith(String name);

    List<Kennel> getAllKennels();

//    List<Kennel> getKennelsByOwner(String ownerId);
}
