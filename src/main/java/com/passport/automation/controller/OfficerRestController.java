package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.dto.ApiResponse;
import com.passport.automation.dto.DashboardStatsDTO;
import com.passport.automation.service.PassportApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/officer")
public class OfficerRestController {

    @Autowired
    private PassportApplicationService applicationService;

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == UserRole.PASSPORT_OFFICER;
    }

    @GetMapping("/dashboard")
    public ApiResponse<Map<String, Object>> dashboard(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        List<PassportApplication> pendingApplications =
                applicationService.getApplicationsByStatus(ApplicationStatus.UNDER_VERIFICATION);

        long submittedCount =
                applicationService.getApplicationCountByStatus(ApplicationStatus.SUBMITTED);
        long underVerificationCount = applicationService
                .getApplicationCountByStatus(ApplicationStatus.UNDER_VERIFICATION);
        long approvedCount =
                applicationService.getApplicationCountByStatus(ApplicationStatus.APPROVED);
        long rejectedCount =
                applicationService.getApplicationCountByStatus(ApplicationStatus.REJECTED);

        Map<String, Object> data = new HashMap<>();
        data.put("applications", pendingApplications);
        data.put("stats",
                new DashboardStatsDTO(
                        submittedCount + underVerificationCount + approvedCount + rejectedCount,
                        submittedCount, underVerificationCount, approvedCount, rejectedCount));

        return new ApiResponse<>(true, "Dashboard retrieved", data);
    }

    @GetMapping("/view/{applicationId}")
    public ApiResponse<PassportApplication> viewApplication(@PathVariable int applicationId,
            HttpSession session) {

        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        PassportApplication application = applicationService.getApplicationById(applicationId);
        if (application == null) {
            return new ApiResponse<>(false, "Application not found");
        }

        return new ApiResponse<>(true, "Application retrieved", application);
    }

    @PostMapping("/approve/{applicationId}")
    public ApiResponse<Void> approveApplication(@PathVariable int applicationId,
            HttpSession session) {

        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        applicationService.approveApplication(applicationId);
        return new ApiResponse<>(true, "Application approved successfully");
    }

    @PostMapping("/reject/{applicationId}")
    public ApiResponse<Void> rejectApplication(@PathVariable int applicationId,
            @RequestParam String reason, HttpSession session) {

        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        applicationService.rejectApplication(applicationId, reason);
        return new ApiResponse<>(true, "Application rejected successfully");
    }

    @GetMapping("/all-applications")
    public ApiResponse<List<PassportApplication>> allApplications(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        List<PassportApplication> applications = applicationService.getAllApplications();
        return new ApiResponse<>(true, "Applications retrieved", applications);
    }
}
