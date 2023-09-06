package com.fosterpet.backend.service;

import com.fosterpet.backend.collection.Kennel;

import java.util.List;

public interface KennelService {
    String save (Kennel kennel);

    List<Kennel> getKennelStartWith(String name);

    List<Kennel> getAllKennels();
}
