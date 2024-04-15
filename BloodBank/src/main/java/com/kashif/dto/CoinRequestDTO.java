package com.kashif.dto;

import com.kashif.entity.UserRegistration;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class CoinRequestDTO {
    private Long id;
    private Integer coins;
    private String reason;
    private String status;
    private Date creationTime;
    private String createdBy;
    private String updatedBy;
    private Date updatedTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private RegistrationDTO userDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public RegistrationDTO getUser() {
        return userDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public RegistrationDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(RegistrationDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setUser(RegistrationDTO userDTO) {
        this.userDTO = userDTO;
    }
}
