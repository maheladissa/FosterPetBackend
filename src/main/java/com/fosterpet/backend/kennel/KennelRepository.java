package com.fosterpet.backend.kennel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface KennelRepository extends MongoRepository <Kennel, String> {

    @Query("{ 'kennelName' : { $regex: '^?0', $options: 'i' } }")
    List<Kennel> findByKennelNameStartsWith(String name);

    List<Kennel> findByOwnerUserId(String ownerId);
    List<Kennel> findAll();

    Kennel findByKennelID(String kennelId);

    @Query("{ 'kennelLocation' : { $near : { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } }, 'isActive': true }")
    List<Kennel> findByLocationNearAndIsActive(double longitude, double latitude, double maxDistance);

    Integer countKennelByIsActive(boolean isActive);

    List<Kennel> findByIsApproved(boolean isApproved);

    @Query("{ 'kennelLocation' : { $near : { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } }, 'isActive': true, 'isApproved': true, 'paymentRates.animalType': ?3 }")
    List<Kennel> findByLocationNearAndIsActiveAndAnimalType(double longitude, double latitude, double maxDistance, String animalType);

    @Query(value = "{ 'createdDate': { $gte: ?0, $lte: ?1 } }", count = true)
    Long countKennelsByTimePeriod(Instant startDate, Instant endDate);

    List<Kennel> findByIsActiveAndAndIsApproved(boolean isActive, boolean isApproved);

    @Query("{ 'createdDate': { $gte: ?0, $lte: ?1 } }")
    List<Kennel> getKennelByTimePeriod(Instant startDate, Instant endDate);
}
