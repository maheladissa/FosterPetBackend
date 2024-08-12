package com.fosterpet.backend.kennel;

import java.util.List;

public interface KennelService {
    KennelResponse save(KennelRequest request);

    List<KennelResponse> getKennelStartWith(String name);

    List<KennelResponse> getAllKennels();

    List<KennelResponse> getKennelsByOwner(String ownerId);

    List<KennelResponse> getKennelsNear(double longitude, double latitude, double maxDistance);

    KennelResponse update(KennelRequest request);

    KennelResponse getKennelById(String kennelId);

    List<KennelResponse> updatePaymentRate(KennelPaymentRateRequest request);

    List<KennelResponse> getAllKennelsPendingApproval();

    KennelResponse approveKennel(String kennelId);

    KennelResponse rejectKennel(String kennelId);

    KennelResponse deleteKennel(String kennelId);

    List<KennelResponse> filterKennels(double longitude, double latitude, double maxDistance, String animalType);

    Long countKennelsByTimePeriod(String startDate, String endDate);

    List<KennelResponse> getKennelByTimePeriod(String startDate, String endDate);

    List<KennelResponse> getAllActiveKennels();

    Integer countKennelsByIsActive(boolean isActive);
}
