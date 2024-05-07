package com.kashif.mapper;

import com.kashif.dto.CoinRequestDTO;
import com.kashif.entity.CoinRequest;
import com.kashif.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    @Autowired
    private UserRegistrationService userRegistrationService;


    //------------------Coins Conversion------------------
    public CoinRequestDTO convertToCoinRequestDTO(CoinRequest coinRequest) {
        CoinRequestDTO dto = new CoinRequestDTO();
        dto.setId(coinRequest.getId());
        dto.setCoins(coinRequest.getCoins());
        dto.setReason(coinRequest.getReason());
        dto.setStatus(coinRequest.getStatus());
        dto.setCreationTime(coinRequest.getCreationTime());
        dto.setCreatedBy(coinRequest.getCreatedBy());
        dto.setUpdatedTime(coinRequest.getUpdatedTime());
        dto.setUpdatedBy(coinRequest.getUpdatedBy());
        dto.setUser(userRegistrationService.convertEntityToDto(coinRequest.getUser()));
        return dto;
    }
    public CoinRequest convertToCoinRequestEntity(CoinRequestDTO dto) {
        CoinRequest entity = new CoinRequest();
        entity.setCoins(dto.getCoins());
        entity.setReason(dto.getReason());
        entity.setCreationTime(dto.getCreationTime());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedTime(dto.getUpdatedTime());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUser(userRegistrationService.getUserRegistration(dto.getUser()));
        return entity;
    }

}
