package com.kashif.repository;

import com.kashif.entity.BloodStock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BloodStockRepo extends MongoRepository<BloodStock, String> {

    List<BloodStock> findAll();
    @Transactional
    @Query("{'bloodGroup': ?3}")
    void updateBloodStockByBloodGroup(Long unit, Date lastUpdate, String bloodGroup);
    Optional<BloodStock> findByBloodGroup(String bloodGroup);
}
