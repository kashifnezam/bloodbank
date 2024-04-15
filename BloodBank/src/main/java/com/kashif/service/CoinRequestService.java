package com.kashif.service;


import com.kashif.dto.CoinRequestDTO;
import com.kashif.entity.CoinRequest;
import com.kashif.mapper.Mapper;
import com.kashif.repository.CoinRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinRequestService {
    @Autowired
    private Mapper mapper;
    @Autowired
    private CoinRequestRepo coinRequestRepo;

    public void registerCoinRequests(CoinRequestDTO dto) {
        CoinRequest coinRequest = mapper.convertToCoinRequestEntity(dto);
        coinRequest.setStatus("pending");
        coinRequestRepo.save(coinRequest);
    }

    public List<CoinRequestDTO> coinRequestList(){
        return coinRequestRepo.findAll()
                .stream().peek(System.out::println).map(i->mapper.convertToCoinRequestDTO(i))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }
}
