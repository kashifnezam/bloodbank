package com.kashif.repository;

import com.kashif.entity.BloodRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface BloodRequestRepo extends CrudRepository<BloodRequest, Long> {
    List<BloodRequest> findAll();

    @Transactional
    @Modifying
    @Query("UPDATE BloodRequest b SET b.status = ?1, b.remark = ?2, b.updatedTime = ?3, b.updatedBy = ?4 WHERE b.id = ?5")
    void updateStatusAndRemarkById(String status, String remark, Date updateTime, String updatedBy, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE BloodRequest b SET b.notification = ?1, b.updatedTime = ?2 WHERE b.id = ?3")
    void setNotification(String notification, Date date, Long id);

    List<BloodRequest> findByCreatedBy(String username); //List of bloodRequest by Username
    Optional<BloodRequest> findById(Long id); // BloodRequest single query
}
