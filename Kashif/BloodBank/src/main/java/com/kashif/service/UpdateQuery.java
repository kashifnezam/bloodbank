package com.kashif.service;

import com.kashif.entity.BloodRequest;
import com.kashif.entity.BloodStock;
import com.kashif.entity.UserRegistration;
import com.kashif.repository.BloodRequestRepo;
import com.kashif.repository.BloodStockRepo;
import com.kashif.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class UpdateQuery {
    @Autowired
    private BloodRequestRepo requestRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BloodStockRepo bloodStockRepo;

    public void updateStatusAndRemarkById(String status, String remark, String username, String id){
        Optional<BloodRequest> userReq = requestRepo.findById(id);
        BloodRequest userRequest = userReq.get();
        userRequest.setStatus(status);
        userRequest.setRemark(remark);
        userRequest.setUpdatedBy(username);
        userRequest.setUpdatedTime(new Date());
        requestRepo.save(userRequest);
    }

    public void updateWrongAttemptsByUsername(int wrongAttempts, String username){
        Optional<UserRegistration> user = userRepo.findByUsername(username);
        user.get().setWrongAttempts(wrongAttempts);
        userRepo.save(user.get());
    }

   public void updateBloodStockByBloodGroup(long totalUnits, Date lastUpdate, String bloodGroup){
       Optional<BloodStock> bGrp = bloodStockRepo.findByBloodGroup(bloodGroup);
       BloodStock stock = bGrp.get();
       stock.setLastUpdate(lastUpdate);
       stock.setUnit(totalUnits);
       bloodStockRepo.save(stock);
   }

    void setNotification(String notification, Date date, String id){
        Optional<BloodRequest> userReq = requestRepo.findById(id);
        BloodRequest userRequest = userReq.get();
        userRequest.setNotification(notification);
        userRequest.setUpdatedTime(date);
        requestRepo.save(userRequest);
    }

    void updateCoins(String username, Long coins){
        Optional<UserRegistration> user = userRepo.findByUsername(username);
        user.get().setCoins(coins);
        userRepo.save(user.get());

    }

}
