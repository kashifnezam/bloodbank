package com.kashif.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@Document(collection = "users_reg") // Define MongoDB collection name
public class UserRegistration {
    @Id // MongoDB ID annotation
    private String id; // MongoDB uses String for id by default
    private String email;
    private String name;
    private String password;
    private LocalDate dob;
    private String username;
    private String role;
    private String address;
    private Date creationTime;
    private String createdBy;
    private String modifyBy;
    private Date updatedTime;

    //    ---Three New Fields Added -----
    private boolean newUser;
    private boolean blockedStatus;
    private String bloodGroup;

    // ----One More Fields Added ---- 14/Mar/2024
    private Long commission;
    private Integer wrongAttempts;  // For wrong password --- 03/April/2024
    private Long coins;     // Added on April 7 as I was directly calculating

    // Added on 9/Apr/2024
    private String securityQ1;
    private String securityQ2;
    private String securityQ3;

    //Added on May 5, 2024
    private String profilePicURL;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public boolean isBlockedStatus() {
        return blockedStatus;
    }

    public void setBlockedStatus(boolean blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Integer getWrongAttempts() {
        return wrongAttempts;
    }

    public void setWrongAttempts(Integer wrongAttempts) {
        this.wrongAttempts = wrongAttempts;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public String getSecurityQ1() {
        return securityQ1;
    }

    public void setSecurityQ1(String securityQ1) {
        this.securityQ1 = securityQ1;
    }

    public String getSecurityQ2() {
        return securityQ2;
    }

    public void setSecurityQ2(String securityQ2) {
        this.securityQ2 = securityQ2;
    }

    public String getSecurityQ3() {
        return securityQ3;
    }

    public void setSecurityQ3(String securityQ3) {
        this.securityQ3 = securityQ3;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }
}
