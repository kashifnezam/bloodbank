package com.kashif.dto;


import org.springframework.stereotype.Component;

@Component
public class BloodPriceDTO {

    // Method to calculate price based on blood group for donation
    public Long donationPrice(String bloodGroup) {
        switch (bloodGroup.toUpperCase()) {
            case "A+":
                return 15L;
            case "A-":
                return 20L;
            case "B+":
                return 5L;
            case "B-":
                return 10L;
            case "AB+":
                return 30L;
            case "AB-":
                return 25L;
            case "O+":
                return 35L;
            case "O-":
                return 40L;
            default:
                throw new IllegalArgumentException("Invalid blood group: " + bloodGroup);
        }
    }

    public Long receivePrice(String bloodGroup) {
        switch (bloodGroup.toUpperCase()) {
            case "A+":
                return 25L;
            case "A-":
                return 40L;
            case "B+":
                return 10L;
            case "B-":
                return 15L;
            case "AB+":
                return 30L;
            case "AB-":
                return 29L;
            case "O+":
                return 50L;
            case "O-":
                return 80L;
            default:
                throw new IllegalArgumentException("Invalid blood group: " + bloodGroup);
        }
    }

}
