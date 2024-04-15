package com.kashif.controller;

import com.kashif.dto.LoginDTO;
import com.kashif.dto.RegistrationDTO;
import com.kashif.entity.BloodRequest;
import com.kashif.service.BloodRequestService;
import com.kashif.service.UtilityService;
import com.kashif.service.UserRegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class AuthenticationController {

    @Autowired
    private UtilityService utilityService;
    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    // ----- SignUp Page ----
    @GetMapping("/signup")
    public String signup(HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData != null) return "redirect:/dashboard";
        return "signup";

    }

    // Users get Registered and Success Message

    @PostMapping("/registration")
    public String endUserRegistration(RegistrationDTO registrationDTO, HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData != null) {
            return "redirect:/dashboard";
        }
        if (registrationDTO.getUsername() == null || registrationDTO.getDob() == null || registrationDTO.getPassword() == null || registrationDTO.getBloodGroup() == null || registrationDTO.getName() == null) {
            mp.addAttribute("errorMsg", "Fields can not be null");
            return "signup";
        }
        if (registrationDTO.getUsername().isBlank() || registrationDTO.getDob().isBlank() || registrationDTO.getPassword().isBlank() || registrationDTO.getBloodGroup().isBlank() || registrationDTO.getName().isBlank()) {
            mp.addAttribute("errorMsg", "Fields can not be Blank");
            return "signup";
        }
        if (userRegistrationService.existsByUsername(registrationDTO.getUsername())) {
            mp.addAttribute("errorMsg", "Username Already Exists");
            return "signup";
        }

        session.setAttribute("registrationDTO", registrationDTO);
        return "redirect:/register-security-questions";

    }

    @GetMapping("/register-security-questions")
    public String securityQuestions(HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        RegistrationDTO registrationDTO = (RegistrationDTO) session.getAttribute("registrationDTO");
        if (userData == null && registrationDTO == null) {
            return "redirect:/login";
        }
        return "register-security-questions";
    }

    @PostMapping("/register-security-questions")
    public String securityQuestionsSubmit(@RequestParam Map<String, String> map, HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        RegistrationDTO registrationDTO = (RegistrationDTO) session.getAttribute("registrationDTO");
        if (userData == null && registrationDTO == null) {
            return "redirect:/login";
        }
        String q1 = map.get("question-1");
        String q2 = map.get("question-2");
        String q3 = map.get("question-3");
        // Validate whether the questions are blank or not
        if (StringUtils.isBlank(q1) || StringUtils.isBlank(q2) || StringUtils.isBlank(q3)) {
            mp.addAttribute("errorMsg", "All Security Questions are required");
            return "register-security-questions";
        }
        if (userData == null) {
            registrationDTO.setSecurityQ1(q1);
            registrationDTO.setSecurityQ2(q2);
            registrationDTO.setSecurityQ3(q3);
            userRegistrationService.registerUser(registrationDTO);
            mp.addAttribute("success", "Account Created Successfully");
            session.invalidate();
            return "login";
        }
        userRegistrationService.setSecurityQuestions(userData.getUsername(), q1, q2, q3);
        mp.addAttribute("success", "Security Questions Updated Successfully");
        RegistrationDTO user = userRegistrationService.getUserByUsername(userData.getUsername()).get();
        session.setAttribute("data", user);
        if (userData.isNewUser()) return "update-password";
        return userData.getRole()
                .equalsIgnoreCase("agent")
                ? "agent/dashboard"
                : "enduser/dashboard";
    }

    @GetMapping("forgot-password")
    public String forgotPassword(HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData != null) {
            return "redirect:/dashboard";
        }
        return "reset-security-question";
    }

    @PostMapping("forgot-password")
    public String forgotPasswordSubmit(@RequestParam Map<String, String> map, Model mp, HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData != null) {
            return "redirect:/dashboard";
        }
        String username = map.get("username").toLowerCase();
        String question = map.get("security-question");
        String ans = map.get("answer");
        System.out.println(question);

        if (StringUtils.isBlank(username)) {
            mp.addAttribute("errorMsg", "Username can't be Blank");
            return "reset-security-question";
        }
        if (!userRegistrationService.existsByUsername(username)) {
            mp.addAttribute("errorMsg", "Username is not valid");
            return "reset-security-question";
        }
        RegistrationDTO user = userRegistrationService.getUserByUsername(username).get();
        if (StringUtils.isBlank(question) || StringUtils.isBlank(ans)) {
            mp.addAttribute("errorMsg", "Answer should not be Blank");
            return "reset-security-question";
        }

        //Verifying Question 1
        if (question.equalsIgnoreCase("q1")) {
            if (user.getSecurityQ1() != null && user.getSecurityQ1().equalsIgnoreCase(ans)) {
                session.setAttribute("data", user);
                return "update-password";
            } else {
                mp.addAttribute("errorMsg", "Security answer is not correct");
                return "reset-security-question";
            }
        }
        //Verifying Question 2
        if (question.equalsIgnoreCase("q2")) {
            if (user.getSecurityQ2() != null && user.getSecurityQ2().equalsIgnoreCase(ans)) {
                session.setAttribute("data", user);
                return "update-password";
            } else {
                mp.addAttribute("errorMsg", "Security answer is not correct");
                return "reset-security-question";
            }
        }
        //Verifying Question 3
        if (question.equalsIgnoreCase("q3")) {
            if (user.getSecurityQ3() != null && user.getSecurityQ3().equalsIgnoreCase(ans)) {
                session.setAttribute("data", user);
                return "update-password";
            } else {
                mp.addAttribute("errorMsg", "Security answer is not correct");
                return "reset-security-question";
            }
        }
        return "reset-security-question";
    }


    // ------ Login Page -----
    @GetMapping("/login")
    public String login(HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData != null) return "redirect:/dashboard";
        return "login";
    }


    //----- Got the Login details and redirected to Dashboard -----
    @PostMapping("/login")
    public String loginChk(LoginDTO loginDTO, HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData != null) return "redirect:/dashboard";
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            mp.addAttribute("errorMsg", "Fields can not be blank");
            return "login";
        }
        if (!userRegistrationService.existsByUsername(username)) {
            mp.addAttribute("errorMsg", "Login failed: Username does not exist");
            return "login";
        }
        if (userRegistrationService.getBlockedStatusByUsername(username)) {
            mp.addAttribute("errorMsg", "User BLOCKED ");
            return "login";
        }
        String pass = userRegistrationService.getPasswordByUsername(username);
        int wrongAttempts = userRegistrationService.getWrongAttemptsByUsername(username);
        if (pass.equals(password)) {
            userRegistrationService.updateWrongAttempts(0, username);
            RegistrationDTO user = userRegistrationService.getUserByUsername(username).get();
            session.setAttribute("data", user);
            utilityService.updateActiveUsers(user.getUsername(), "add");
            return "redirect:/dashboard";
        } else {
            userRegistrationService.updateWrongAttempts(wrongAttempts + 1, username);
            wrongAttempts = userRegistrationService.getWrongAttemptsByUsername(username);
        }

        if (wrongAttempts > 3) {
            userRegistrationService.updateBlockedStatusByUsername(true, username);
            session.invalidate(); // Invalidate the session to clear cache
            mp.addAttribute("errorMsg", "User BLOCKED ");
            return "login";
        }
        mp.addAttribute("errorMsg", "Invalid Password ");
        mp.addAttribute("times", wrongAttempts);
        return "login";
    }


    // ------ Password Update Page ------
    @GetMapping("/update-password")
    public String changePassword(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "login";
        }
        if (userData.getSecurityQ1() == null) return "redirect:/register-security-questions";
        return "update-password";
    }

    // ----- Updating Password and changing the newUser to False -----
    @PostMapping("/update-password")
    public String validatePassword(@RequestParam(required = false) String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (userData.getSecurityQ1() == null) return "redirect:/register-security-questions";
        if (confirmPassword.isBlank() || newPassword == null || newPassword.isBlank()) {
            mp.addAttribute("errorMsg", "Field Can't be Empty");
            return "update-password";
        }
        if (userData.isNewUser() && currentPassword == null) {
            mp.addAttribute("errorMsg", "Current Password Can't be Empty");
            return "update-password";
        }
        if (userData.isNewUser() && !currentPassword.equals(userData.getPassword())) {
            mp.addAttribute("errorMsg", "Wrong Password");
            return "update-password";
        }
        if (!newPassword.equals(confirmPassword)) {
            mp.addAttribute("errorMsg", "Password didn't Match.");
            return "update-password";
        }
        if (userData.getPassword().equals(newPassword)) {
            mp.addAttribute("errorMsg", "Can't set old password");
            return "update-password";
        }


        String username = userData.getUsername().toLowerCase();

        //---------- Updating the Password ---------
        userRegistrationService.updatePasswordByUsername(newPassword, username);
        userRegistrationService.updateNewUserByUsername(false, username);
        userRegistrationService.updateWrongAttempts(0, username);
        userRegistrationService.updateBlockedStatusByUsername(false, username);
        RegistrationDTO user = (RegistrationDTO) session.getAttribute("data");
        utilityService.updateActiveUsers(user.getUsername(), "remove");
        session.invalidate();
        mp.addAttribute("success", "Password updated successfully.");
        return "login";
    }

    //----- After redirecting to Dashboard -----
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }

        ArrayList<HashMap<String, Object>> list = utilityService.getAgentAndAdminCoins(userData.getUsername(), userData.getRole());
        double agentTotalCoins = list.stream().mapToDouble(i -> (double) i.get("agentCoins")).sum();
        double adminTotalCoins = list.stream().mapToDouble(i -> (double) i.get("adminCoins")).sum();
        if (userData.getRole().equalsIgnoreCase("admin")) {
            mp.addAttribute("allUsers", userRegistrationService.getAllUsers());
            mp.addAttribute("adminTotalCoins", adminTotalCoins);
            return "admin/dashboard";
        }
        if (userData.getRole().equals("agent")) {
            mp.addAttribute("agentTotalCoins", agentTotalCoins);
            return "agent/dashboard";
        }
        mp.addAttribute("totalCoins", utilityService.getEndUserCoins(userData.getUsername()));
        List<BloodRequest> notify = bloodRequestService.getBloodRequestByUsername(userData.getUsername())
                .stream().filter(i -> i.getNotification() != null)
                .collect(Collectors.toList());
        Collections.reverse(notify);
        session.setAttribute("notificationList", notify);

        return "enduser/dashboard";
    }


//    -------------------- Here Different Task: 14 Mar 2024-------------------

    @GetMapping("/dashboard/agent-account-creation")
    public String agentRegistration(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (!userData.getRole().equalsIgnoreCase("admin")) {
            mp.addAttribute("errorMsg", "Not Allowed (You are not Admin)");
            return "redirect:/dashboard";
        }

        return "admin/agent-signup";
    }

    @PostMapping("/dashboard/agent-account-creation")
    public String agentRegistrationSubmit(RegistrationDTO registrationDTO, HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (!userData.getRole().equalsIgnoreCase("admin")) {
            return "redirect:/dashboard";
        }
        if (registrationDTO.getUsername() == null || registrationDTO.getDob() == null || registrationDTO.getPassword() == null || registrationDTO.getBloodGroup() == null || registrationDTO.getName() == null) {
            mp.addAttribute("errorMsg", "Fields can not be null");
            return "admin/agent-signup";
        }
        if (registrationDTO.getUsername().isBlank() || registrationDTO.getDob().isBlank() || registrationDTO.getPassword().isBlank() || registrationDTO.getBloodGroup().isBlank() || registrationDTO.getName().isBlank()) {
            mp.addAttribute("errorMsg", "Fields can not be blank");
            return "admin/agent-signup";
        }
        if (registrationDTO.getCommission() > 70) {
            mp.addAttribute("errorMsg", "Maximum Commission should be 70 %");
            return "admin/agent-signup";
        }

        if (userRegistrationService.existsByUsername(registrationDTO.getUsername())) {
            mp.addAttribute("errorMsg", "Username Already Exists");
            return "admin/agent-signup";
        }

        registrationDTO.setPassword(registrationDTO.getDob());
        registrationDTO.setBloodGroup("-");
        registrationDTO.setCreatedBy("admin");
        registrationDTO.setRole("agent");
        userRegistrationService.registerUser(registrationDTO);
        mp.addAttribute("success", "Account Created Successfully");
        return "admin/agent-signup";
    }

    @GetMapping("/dashboard/enduser-account-creation")
    public String agentDashboard(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (userData.getRole().equalsIgnoreCase("EndUser")) {
            return "redirect:/dashboard";
        }
        return "agent/enduser-signup";
    }

    @PostMapping("/dashboard/enduser-account-creation")
    public String endUserRegistrationByAgent(RegistrationDTO registrationDTO, HttpSession session, Model mp) {

        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");

        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (userData.getRole().equalsIgnoreCase("EndUser")) {
            return "redirect:/dashboard";
        }
        if (StringUtils.isBlank(registrationDTO.getUsername()) ||
                StringUtils.isBlank(registrationDTO.getDob()) ||
                StringUtils.isBlank(registrationDTO.getBloodGroup()) ||
                StringUtils.isBlank(registrationDTO.getName())) {
            mp.addAttribute("errorMsg", "Fields can not be null");
            return "agent/enduser-signup";
        }

        if (userRegistrationService.existsByUsername(registrationDTO.getUsername())) {
            mp.addAttribute("errorMsg", "Username Already Exists");
            return "agent/enduser-signup";
        }
        registrationDTO.setCreatedBy(userData.getUsername());
        registrationDTO.setPassword(registrationDTO.getDob());

        userRegistrationService.registerUser(registrationDTO);
        mp.addAttribute("success", "Account created successfully");
        return "agent/enduser-signup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        RegistrationDTO user = (RegistrationDTO) session.getAttribute("data");
        if (user == null) return "redirect:/login";
        utilityService.updateActiveUsers(user.getUsername(), "remove");
        session.invalidate();
        return "redirect:/login";
    }
}
