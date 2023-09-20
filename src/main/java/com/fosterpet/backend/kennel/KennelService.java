package com.fosterpet.backend.kennel;

import java.util.List;

public interface KennelService {
    String save (Kennel kennel);

    List<Kennel> getKennelStartWith(String name);

    List<Kennel> getAllKennels();
}
