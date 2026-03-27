package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.dto.ApiResponse;
import com.passport.automation.service.PassportApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/police")
public class PoliceRestController {

    @Autowired
    private PassportApplicationService applicationService;

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == UserRole.POLICE_VERIFICATION_OFFICER;
    }

    @GetMapping("/dashboard")
    public ApiResponse<List<PassportApplication>> dashboard(HttpSession session) {
        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        List<PassportApplication> pendingVerifications =
                applicationService.getApplicationsForPoliceVerification();

        return new ApiResponse<>(true, "Dashboard retrieved", pendingVerifications);
    }

    @GetMapping("/verify/{applicationId}")
    public ApiResponse<PassportApplication> viewApplicationForVerification(
            @PathVariable int applicationId, HttpSession session) {

        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        PassportApplication application = applicationService.getApplicationById(applicationId);
        if (application == null) {
            return new ApiResponse<>(false, "Application not found");
        }

        return new ApiResponse<>(true, "Application retrieved", application);
    }

    @PostMapping("/verify/{applicationId}")
    public ApiResponse<Void> updateVerificationStatus(@PathVariable int applicationId,
            @RequestParam VerificationStatus status, HttpSession session) {

        if (!isAuthorized(session)) {
            return new ApiResponse<>(false, "Not authorized");
        }

        applicationService.updatePoliceVerification(applicationId, status);
        return new ApiResponse<>(true, "Verification status updated successfully");
    }
}
