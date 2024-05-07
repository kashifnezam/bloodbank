package com.kashif.repository;

import com.kashif.entity.BloodRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BloodRequestRepo extends MongoRepository<BloodRequest, String> {
    List<BloodRequest> findAll();

    @Query("{'_id': ?2,'remark':?1 'status':?0}")
    void updateStatusAndRemarkById(String status, String remark, String id);

    @Query("{'_id': ?2}")
    void setNotification(String notification, Date date, String id);

    List<BloodRequest> findByCreatedBy(String username); // List of bloodRequest by Username

    Optional<BloodRequest> findById(String id); // BloodRequest single query
}
