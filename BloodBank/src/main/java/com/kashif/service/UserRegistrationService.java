package com.kashif.service;

import com.kashif.dto.RegistrationDTO;
import com.kashif.entity.UserRegistration;
import com.kashif.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRegistrationService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRegistration userRegistration;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BloodRequestService bloodRequestService;


    public RegistrationDTO registerUser(RegistrationDTO registrationDTO) {
        if (registrationDTO.getRole() == null) {
            userRegistration.setRole("EndUser");
        } else {
            userRegistration.setRole(registrationDTO.getRole());
        }
        if (registrationDTO.getCreatedBy() == null) {
            userRegistration.setCreatedBy("Self");
        } else {
            userRegistration.setCreatedBy(registrationDTO.getCreatedBy());
        }
        if (registrationDTO.getCommission() == null) {
            userRegistration.setCommission(0L);
        }
        userRegistration.setUsername(registrationDTO.getUsername().toLowerCase());
        userRegistration.setName(registrationDTO.getName());
        userRegistration.setEmail(registrationDTO.getEmail());
        userRegistration.setAddress(registrationDTO.getAddress());
        userRegistration.setDob(LocalDate.parse(registrationDTO.getDob()));
        userRegistration.setBloodGroup(registrationDTO.getBloodGroup());
        userRegistration.setPassword(registrationDTO.getPassword());
        userRegistration.setCommission(registrationDTO.getCommission());
        userRegistration.setCreationTime(new Date());
        //Security Questions
        userRegistration.setSecurityQ1(registrationDTO.getSecurityQ1());
        userRegistration.setSecurityQ2(registrationDTO.getSecurityQ2());
        userRegistration.setSecurityQ3(registrationDTO.getSecurityQ3());

        //--------------- Extra fields Default data -------
        if ((!userRegistration.getRole().equalsIgnoreCase("admin") && userRegistration.getRole().equalsIgnoreCase("EndUser") && userRegistration.getCreatedBy().equalsIgnoreCase("Self")))
            userRegistration.setNewUser(false);
        else userRegistration.setNewUser(true);
        userRegistration.setBlockedStatus(false);
        userRegistration.setUpdatedTime(new Date());
        userRegistration.setModifyBy("NA");
        userRegistration.setId(0);
        userRepo.save(userRegistration);
        return registrationDTO;
    }

    public UserRegistration getUserRegistration(RegistrationDTO dto) {
        return userRepo.findByUsername(dto.getUsername()).get();
    }

    public RegistrationDTO convertEntityToDto(UserRegistration userRegistration) {
        return this.modelMapper.map(userRegistration, RegistrationDTO.class);
    }

    public List<RegistrationDTO> getAllUsers() {
        return userRepo.findAll().stream().filter(user -> !user.getUsername().equalsIgnoreCase("admin")).map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return getUserByUsername(username).isPresent();
    }

    public boolean getBlockedStatusByUsername(String username) {
        return getUserByUsername(username).isPresent() && getUserByUsername(username).get().isBlockedStatus();
    }


    public void updateBlockedStatusByUsername(boolean blockedStatus, String username) {
        userRepo.updateBlockedStatusByUsername(blockedStatus, username);
    }

    public String getPasswordByUsername(String username) {
        return getUserByUsername(username).isPresent() ? getUserByUsername(username).get().getPassword() : "";
    }


    public void updatePasswordByUsername(String newPassword, String username) {
        userRepo.updatePasswordByUsername(newPassword, username);
    }

    public void updateNewUserByUsername(boolean newUserStatus, String username) {
        userRepo.updateNewUserByUsername(newUserStatus, username);
    }

    public Optional<RegistrationDTO> getUserByUsername(String username) {
        Optional<UserRegistration> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            RegistrationDTO registrationDTO = convertEntityToDto(user.get());
            return Optional.of(registrationDTO);
        }

        return Optional.empty();
    }

    public int getWrongAttemptsByUsername(String username) {
        return getUserByUsername(username).get().getWrongAttempts();
    }

    public void updateWrongAttempts(int wrongAttempts, String username) {
        userRepo.updateWrongAttemptsByUsername(wrongAttempts, username);
    }

    // Update Coins
    public void updateCoins(String username, Long coins) {
        Optional<RegistrationDTO> user = getUserByUsername(username);
        if (user.isPresent()) {
            coins += user.get().getCoins() == null ? 0 : user.get().getCoins();
        }
        userRepo.updateCoins(username, coins);
    }

    //Set Security Questions
    public void setSecurityQuestions(String username, String q1, String q2, String q3) {
        userRepo.setSecurityQuestions(username, q1, q2, q3);
    }

}
