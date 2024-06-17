package com.fosterpet.backend.kennel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KennelRepository extends MongoRepository <Kennel, String> {

    @Query("{ 'kennelName' : { $regex: '^?0', $options: 'i' } }")
    List<Kennel> findByKennelNameStartsWith(String name);

    List<Kennel> findByOwnerUserId(String ownerId);
    List<Kennel> findAll();

    Kennel findByKennelID(String kennelId);

    @Query("{ 'kennelLocation' : { $near : { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } } }")
    List<Kennel> findByLocationNear(double longitude, double latitude, double maxDistance);

    Integer countKennelByIsActive(boolean isActive);

    List<Kennel> findByIsApproved(boolean isApproved);
}
