package com.kashif.repository;

import com.kashif.entity.CoinRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRequestRepo extends MongoRepository<CoinRequest, Long> {
    List<CoinRequest> findAll();
}
