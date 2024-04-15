package com.kashif.repository;

import com.kashif.entity.CoinRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRequestRepo extends CrudRepository<CoinRequest, Long> {
    List<CoinRequest> findAll();
}
