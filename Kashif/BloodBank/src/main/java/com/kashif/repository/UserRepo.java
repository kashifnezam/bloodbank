package com.kashif.repository;

import com.kashif.entity.UserRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<UserRegistration, String> {

    @Query("{'username': ?2}")
    void updatePasswordByUsername(String password, String username);

    @Query("{'username': ?2}")
    void updateNewUserByUsername(boolean newUser, String username);

    @Query("{'username': ?2}")
    void updateBlockedStatusByUsername(boolean blockedStatus, String username);

    @Query("{'username': ?2}")
    void updateWrongAttemptsByUsername(int wrongAttempts, String username);

    @Query("{'username': ?1}")
    void updateCoins(String username, Long coins);


    @Query("{'username': ?1}")
    void setSecurityQuestions(String username, String securityQ1, String securityQ2, String securityQ3);

    boolean existsByRole(String role);

    List<UserRegistration> findAll();

    Optional<UserRegistration> findByUsername(String username);
}
