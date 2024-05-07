package com.kashif.controller;

import com.kashif.dto.DataTable;
import com.kashif.dto.RegistrationDTO;
import com.kashif.entity.UserRegistration;
import com.kashif.service.BloodRequestService;
import com.kashif.service.UserRegistrationService;
import com.kashif.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private UtilityService service;
    @Autowired
    private UserRegistrationService registrationService;

    // --- Homepage where users can go to signin/signup page ------
    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @ResponseBody
    @GetMapping("blood-users/get")
    public ResponseEntity<Object> getAllEmployee(@RequestParam("draw") int draw,
                                                 @RequestParam("start") int start,
                                                 @RequestParam("length") int length) {
        int page = start / length; //Calculate page number
        Page<UserRegistration> allUsers= registrationService.getAllUsersForDataTables(page, length);
        DataTable dataTable = new DataTable();
        dataTable.setData(allUsers.getContent());
        dataTable.setRecordsTotal(allUsers.getTotalElements());
        dataTable.setRecordsFiltered(allUsers.getTotalElements());
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        return ResponseEntity.ok(dataTable);
    }

}
