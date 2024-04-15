package com.kashif.controller;

import com.kashif.dto.*;
import com.kashif.entity.BloodRequest;
import com.kashif.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller()
public class UtilityBloodController {


    @Autowired
    private UtilityService utilityService;
    @Autowired
    private BloodRequestService bloodRequestService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private BloodStockService bloodStockService;
    @Autowired
    private CoinRequestService coinRequestService;

    @GetMapping("/dashboard/donate-blood")
    public String donateBlood(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        return "enduser/donate-blood";
    }

    @GetMapping("/dashboard/receive-blood")
    public String receiveBlood(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }

        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }

        return "enduser/receive-blood";
    }

    @PostMapping(value = {"/dashboard/donate-blood", "/dashboard/receive-blood"})
    public String receiveBloodSubmit(BloodRequestDTO bloodRequestDTO, HttpSession session, @RequestParam String bloodGroup, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        List<BloodRequest> bloodRequest = bloodRequestService.getBloodRequestByUsername(userData.getUsername());
        Optional<BloodRequest> pendingDonor = bloodRequest.stream()
                .filter(i -> i.getType().equalsIgnoreCase("donor"))
                .filter(i -> i.getStatus().equalsIgnoreCase("pending"))
                .findFirst();
        Optional<BloodRequest> pendingReceiver = bloodRequest.stream()
                .filter(i -> i.getType().equalsIgnoreCase("receiver"))
                .filter(i -> i.getStatus().equalsIgnoreCase("pending"))
                .findFirst();
        if (bloodRequestDTO.getType().equalsIgnoreCase("donor") && pendingDonor.isPresent()) {
            mp.addAttribute("errorMsg", "You already have requested for Req ID: " + pendingDonor.get().getId());
            return "enduser/donate-blood";
        }
        if (bloodRequestDTO.getType().equalsIgnoreCase("receiver") && pendingReceiver.isPresent()) {
            mp.addAttribute("errorMsg", "You already have requested for Req ID: " + pendingReceiver.get().getId());
            return "enduser/receive-blood";
        }
        if (bloodRequestDTO.getQuantity() == null || bloodRequestDTO.getQuantity() <= 0) {
            mp.addAttribute("errorMsg", "Quantity must be positive.");
            if (bloodRequestDTO.getType().equalsIgnoreCase("donor")) return "enduser/donate-blood";
            return "enduser/receive-blood";
        }
        if (bloodRequestDTO.getType().equalsIgnoreCase("donor")) {
            if (Period.between(LocalDate.parse(userData.getDob()), LocalDate.now()).getYears() < 18) {
                mp.addAttribute("errorMsg", "You'r underage! Request when you are 18 years Old");
                return "enduser/donate-blood";
            }
            String msg = utilityService.eligibleToDonate(userData.getUsername());
            if (!msg.equalsIgnoreCase("ok")) {
                mp.addAttribute("errorMsg", msg);
                return "enduser/donate-blood";
            }
        }


        bloodRequestDTO.setBloodGroup(bloodGroup);
        bloodRequestDTO.setCreatedBy(userData.getUsername());
        bloodRequestDTO.setUser(userData);
        bloodRequestService.addBloodInfo(bloodRequestDTO);
        mp.addAttribute("success", "Request Successfully: check status");
        if (bloodRequestDTO.getType().equalsIgnoreCase("donor")) return "enduser/donate-blood";
        return "enduser/receive-blood";
    }

    @GetMapping("/dashboard/blood-request")
    public String bloodRequest(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "redirect:/login";
        }
        if (!userData.getRole().equalsIgnoreCase("admin")) return "redirect:/dashboard";
        mp.addAttribute("bloodStocks", bloodRequestService.getBloodBankList());
        return "admin/blood-request";
    }

    @PostMapping("/dashboard/blood-request")
    public String bloodRequestUpdate(@RequestParam Map<String, String> requestParams, Model mp, HttpSession session) throws ParseException {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            mp.addAttribute("errorMsg", "You are not logged In");
            return "login";
        }

        if (!userData.getRole().equalsIgnoreCase("admin")) return "redirect:/dashboard";
        long id = requestParams.get("id") != null ? Long.parseLong(requestParams.get("id")) : 0;
        String bloodStatus = requestParams.get("bloodStatus");
        String remark = requestParams.get("remark") == null || requestParams.get("remark").isBlank() ? "-" : requestParams.get("remark");
        String notification = requestParams.get("notification");
        long quantity = requestParams.get("quantity") == null ? 0 : Long.parseLong(requestParams.get("quantity"));
        String bloodGroup = requestParams.get("bloodGroup");
        Long coins = requestParams.get("coins") == null ? 0 : Long.parseLong(requestParams.get("coins"));
        String type = requestParams.get("type");
        String filter = requestParams.get("filterOption");
        if (filter == null && bloodStatus == null) {
            mp.addAttribute("errorMsg", "Field Can't be Empty");
            return "admin/blood-request";
        }
        // Here Request List
        List<HashMap<String, Object>> requestList = bloodRequestService.getBloodBankList();
        mp.addAttribute("bloodStocks", requestList);

        // Get the request blood user
        Optional<BloodRequest> user = bloodRequestService.getBloodRequestById(id);

        if (StringUtils.isNotBlank(notification)) {
            bloodRequestService.setNotification(notification, id);
            mp.addAttribute("success", "Notification sent Successfully");
            return "admin/blood-request";
        }

        if (bloodStatus != null && bloodStatus.equalsIgnoreCase("accepted")) {

            // Checking whether the user has the coin to receive blood or not and updating the user balance
            if (StringUtils.isNotBlank(type) && type.equalsIgnoreCase("receiver") && user.isPresent()) {
                if (Objects.isNull(user.get().getUser().getCoins()) || user.get().getUser().getCoins() < coins) {
                    mp.addAttribute("errorMsg", "Insufficient Balance");
                    return "admin/blood-request";
                }
                // Updating Blood Stock
                String msg = bloodStockService.updateBloodStock(quantity, new Date(), bloodGroup, type, id);
                if (!msg.equalsIgnoreCase("ok")) {
                    mp.addAttribute("errorMsg", msg);
                    return "admin/blood-request";
                }
                userRegistrationService.updateCoins(user.get().getUser().getUsername(), coins * -1);
            }

            if (StringUtils.isNotBlank(type) && type.equalsIgnoreCase("donor")) {
                // Updating Blood Stock
                String msg = bloodStockService.updateBloodStock(quantity, new Date(), bloodGroup, type, id);
                if (!msg.equalsIgnoreCase("ok")) {
                    mp.addAttribute("errorMsg", msg);
                    return "admin/blood-request";
                }
                user.ifPresent(bloodRequest -> userRegistrationService.updateCoins(bloodRequest.getUser().getUsername(), coins));
            }
            mp.addAttribute("success", "Request Accepted Successfully");
        } else mp.addAttribute("success", "Request Rejected Successfully");

        // Updating Blood Stock
        if (bloodStatus != null && bloodStatus.equalsIgnoreCase("accepted")) {
            String msg = bloodStockService.updateBloodStock(quantity, new Date(), bloodGroup, type, id);
            if (!msg.equalsIgnoreCase("ok")) {
                mp.addAttribute("errorMsg", msg);
                return "admin/blood-request";
            }

        }
        bloodRequestService.updateStatusAndRemarkById(bloodStatus, remark, userData.getUsername(), id);
        requestList = bloodRequestService.getBloodBankList();
        mp.addAttribute("bloodStocks", requestList);
        // Date filter applied here
        if (filter != null && filter.equalsIgnoreCase("createdBetween")) {
            String startDate = requestParams.get("startDate");
            String endDate = requestParams.get("endDate");
            mp.addAttribute("bloodStocks", utilityService.filterBloodRequestByDate(startDate, endDate, requestList));
            mp.addAttribute("success", "Filter Applied");
        }
        // Status filter (accept/pending/rejected)
        if (filter != null) {
            String input = filter.equalsIgnoreCase("byStatus") ? requestParams.get("status") : filter.equalsIgnoreCase("byAgent") ? requestParams.get("agent") : filter.equalsIgnoreCase("byUsername") ? requestParams.get("username") : "none";
            mp.addAttribute("success", "Filter Applied");
            requestList = utilityService.filterByQueryBloodRequest(requestList, filter, input);
        }
        mp.addAttribute("bloodStocks", requestList);
        return "admin/blood-request";
    }

    @GetMapping({"/dashboard/alluser-lists", "/dashboard/enduser-lists"})
    public String endUserLists(Model mp, HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (userData.getRole().equalsIgnoreCase("admin")) {
            mp.addAttribute("allUsers", userRegistrationService.getAllUsers());
            return "admin/alluser-lists";
        }
        if (userData.getRole().equalsIgnoreCase("agent")) {
            mp.addAttribute("allUsers", utilityService.filterByCurrentAgent(userRegistrationService.getAllUsers(), userData));
            return "agent/enduser-lists";
        }

        return "redirect:/dashboard";
    }

    @PostMapping({"/dashboard/alluser-lists", "/dashboard/enduser-lists"})
    public String endUserUpdatedLists(HttpSession session, Model mp, @RequestParam Map<String, String> requestParams) throws ParseException {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }

        String sortOption = requestParams.get("sortOption");
        String filterOption = requestParams.get("filterOption");
        String username = requestParams.get("username");
        String startDate = requestParams.get("startDate");
        String endDate = requestParams.get("endDate");
        String agent = requestParams.get("agent");


        List<RegistrationDTO> allUsersList = userRegistrationService.getAllUsers();

        if (userData.getRole().equalsIgnoreCase("agent")) {
            allUsersList = utilityService.filterByCurrentAgent(allUsersList, userData);
        }
        if (sortOption != null) {
            allUsersList = utilityService.sortUsers(sortOption, allUsersList);
            mp.addAttribute("allUsers", allUsersList);
            mp.addAttribute("operation", "Sorting Applied");
        }

        if (filterOption.equalsIgnoreCase("activeUsers")) {
            mp.addAttribute("allUsers", utilityService.getAllActiveUsers());
            mp.addAttribute("operation", "Filtering Applied");
        }

        if (filterOption.equalsIgnoreCase("notLoggedInUser")) {
            mp.addAttribute("allUsers", utilityService.filterByNonLoggedInUser(allUsersList));
            mp.addAttribute("operation", "Filtering Applied");
        }


        if (filterOption.equalsIgnoreCase("byAgent") && !userData.getRole().equalsIgnoreCase("agent")) {
            mp.addAttribute("allUsers", utilityService.filterByAgent(allUsersList, agent));
            mp.addAttribute("operation", "Filtering Applied");
        }

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            mp.addAttribute("allUsers", utilityService.filterByDate(startDate, endDate, allUsersList));
            mp.addAttribute("operation", "Filtering Applied");
        }

        if (username != null && !username.isBlank()) {
            mp.addAttribute("allUsers", utilityService.filterByUsername(username, allUsersList));
            mp.addAttribute("operation", "Filtering Applied");
        }

        return userData.getRole().equalsIgnoreCase("admin") ? "admin/alluser-lists" : "agent/enduser-lists";
    }

    @RequestMapping("/dashboard/status")
    public String statusEndUser(HttpSession session, Model mp, @RequestParam Map<String, String> requestParams) throws ParseException {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }

        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        String filter = requestParams.get("filterOption");
        List<HashMap<String, Object>> list = bloodRequestService.requestBloodStatus(userData.getUsername(), userData.getRole());


        if (filter != null && filter.equalsIgnoreCase("createdBetween")) {
            String startDate = requestParams.get("startDate");
            String endDate = requestParams.get("endDate");
            list = utilityService.filterBloodRequestByDate(startDate, endDate, list);
            mp.addAttribute("success", "Filter Applied");
        }
        // Status filter (accept/pending/rejected)
        if (filter != null) {
            String input = filter.equalsIgnoreCase("byStatus") ? requestParams.get("status") : filter.equalsIgnoreCase("byAgent") ? requestParams.get("agent") : filter.equalsIgnoreCase("byUsername") ? requestParams.get("username") : "none";
            mp.addAttribute("success", "Filter Applied");
            list = utilityService.filterByQueryBloodRequest(list, filter, input);
        }
        mp.addAttribute("bloodRequests", list);
        return userData.getRole().equalsIgnoreCase("agent") ? "agent/status" : "enduser/status";
    }

    @GetMapping("/dashboard/blood-report")
    public String bloodReport(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (userData.getRole().equalsIgnoreCase("EndUser")) return "enduser/dashboard";
        mp.addAttribute("reportAcceptedBG", bloodRequestService.listReport("accepted", userData.getUsername(), userData.getRole()));
        mp.addAttribute("reportRejectedBG", bloodRequestService.listReport("rejected", userData.getUsername(), userData.getRole()));
        return userData.getRole().equalsIgnoreCase("agent") ? "agent/blood-report" : userData.getRole().equalsIgnoreCase("admin") ? "admin/blood-report" : "redirect:/dashboard";
    }

    @GetMapping("/dashboard/available-stock")
    public String availableStock(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        List<BloodStockDTO> list = bloodStockService.allStockList();
        long totalStock = list.stream().mapToLong(BloodStockDTO::getUnit).sum();
        mp.addAttribute("stocks", list);

        mp.addAttribute("totalStock", totalStock);
        return userData.getRole().equalsIgnoreCase("admin") ? "admin/available-stock" : "redirect:/dashboard";
    }

    @GetMapping("/dashboard/coins-value")
    public String coinValue(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }

        BloodPriceDTO coinPrice = bloodRequestService.getBloodPriceDTO();
        mp.addAttribute("coinPrice", coinPrice);
        return userData.getRole().equalsIgnoreCase("agent") ? "agent/coins-value" : "redirect:/dashboard";
    }

    @GetMapping("dashboard/coins-report")
    public String agentCoinReport(HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if (userData.getRole().equalsIgnoreCase("EndUser")) return "redirect:/dashboard";
        ArrayList<HashMap<String, Object>> list = utilityService.getAgentAndAdminCoins(userData.getUsername(), userData.getRole());
        double endUserTotalCoins = list.stream().mapToDouble(i -> (double) i.get("endUserCoins")).sum();
        double agentTotalCoins = list.stream().mapToDouble(i -> (double) i.get("agentCoins")).sum();
        double adminTotalCoins = list.stream().mapToDouble(i -> (double) i.get("adminCoins")).sum();

        mp.addAttribute("agentCoinReport", list);
        mp.addAttribute("endUserTotalCoins", endUserTotalCoins);
        mp.addAttribute("agentTotalCoins", agentTotalCoins);
        mp.addAttribute("adminTotalCoins", adminTotalCoins);
        return userData.getRole().equalsIgnoreCase("admin") ? "admin/coins-report" : "agent/coins-report";
    }

    // Request coins to Admin
    @GetMapping("dashboard/request-coins")
    public String requestCoins(HttpSession session) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if(!userData.getRole().equalsIgnoreCase("enduser"))
            return "redirect:/dashboard";
        return "enduser/request-coins";
    }
    @PostMapping("dashboard/request-coins")
    public String requestCoinsSubmit(CoinRequestDTO coinRequestDTO, HttpSession session, Model mp) {
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if(!userData.getRole().equalsIgnoreCase("enduser"))
            return "redirect:/dashboard";
        String reason = coinRequestDTO.getReason();
        Integer coins = coinRequestDTO.getCoins();
        if(StringUtils.isBlank(reason)){
            mp.addAttribute("errorMsg", "Reason must not Empty");
            return "enduser/request-coins";
        }
        if(coins==null){
            mp.addAttribute("errorMsg", "Coins must not Empty");
            return "enduser/request-coins";
        }

        if(coins<0 || coins>1000){
            mp.addAttribute("errorMsg", "Coins must be between 0 and 1000");
            return "enduser/request-coins";
        }
        coinRequestDTO.setCreationTime(new Date());
        coinRequestDTO.setCreatedBy(userData.getUsername());
        coinRequestDTO.setUser(userData);
        coinRequestService.registerCoinRequests(coinRequestDTO);
        mp.addAttribute("success", "Requested successfully.");
        return "enduser/request-coins";
    }

    @GetMapping("dashboard/coin-request-list")
    public String bloodRequestList(HttpSession session, Model mp){
        RegistrationDTO userData = (RegistrationDTO) session.getAttribute("data");
        if (userData == null) {
            return "redirect:/login";
        }
        if (userData.isNewUser() || userData.isBlockedStatus()) {
            return "redirect:/update-password";
        }
        if(!userData.getRole().equalsIgnoreCase("admin"))
            return "redirect:/dashboard";
        List<CoinRequestDTO> coinList = coinRequestService.coinRequestList();
        mp.addAttribute("coinRequests", coinList);
        return "admin/coin-request";

    }

}
