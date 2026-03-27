package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.dto.ApiResponse;
import com.passport.automation.dto.DashboardStatsDTO;
import com.passport.automation.service.PassportApplicationService;
import com.passport.automation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private PassportApplicationService applicationService;

    @Autowired
    private UserService userService;

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == UserRole.ADMINISTRATOR;
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardStatsDTO> dashboard(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        long totalApplications = applicationService.getAllApplications().size();
        long submittedCount =
                applicationService.getApplicationCountByStatus(ApplicationStatus.SUBMITTED);
        long underVerificationCount = applicationService
                .getApplicationCountByStatus(ApplicationStatus.UNDER_VERIFICATION);
        long approvedCount =
                applicationService.getApplicationCountByStatus(ApplicationStatus.APPROVED);
        long rejectedCount =
                applicationService.getApplicationCountByStatus(ApplicationStatus.REJECTED);

        DashboardStatsDTO stats = new DashboardStatsDTO(totalApplications, submittedCount,
                underVerificationCount, approvedCount, rejectedCount);

        return new ApiResponse<>(true, "Dashboard retrieved", stats);
    }

    @GetMapping("/users")
    public ApiResponse<Map<String, List<User>>> manageUsers(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        List<User> applicants = userService.getUsersByRole(UserRole.APPLICANT);
        List<User> officers = userService.getUsersByRole(UserRole.PASSPORT_OFFICER);
        List<User> policeOfficers =
                userService.getUsersByRole(UserRole.POLICE_VERIFICATION_OFFICER);

        Map<String, List<User>> data = new HashMap<>();
        data.put("applicants", applicants);
        data.put("officers", officers);
        data.put("policeOfficers", policeOfficers);

        return new ApiResponse<>(true, "Users retrieved", data);
    }

    @GetMapping("/applications")
    public ApiResponse<List<PassportApplication>> viewAllApplications(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        List<PassportApplication> applications = applicationService.getAllApplications();
        return new ApiResponse<>(true, "Applications retrieved", applications);
    }

    @GetMapping("/reports")
    public ApiResponse<List<PassportApplication>> generateReports(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        List<PassportApplication> applications = applicationService.getAllApplications();
        return new ApiResponse<>(true, "Reports retrieved", applications);
    }
}
