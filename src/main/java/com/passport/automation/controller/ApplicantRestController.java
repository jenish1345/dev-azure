package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.dto.ApiResponse;
import com.passport.automation.service.PassportApplicationService;
import com.passport.automation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantRestController {

    @Autowired
    private PassportApplicationService applicationService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public ApiResponse<List<PassportApplication>> dashboard(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ApiResponse<>(false, "Not authenticated");
        }

        List<PassportApplication> applications =
                applicationService.getApplicationsByApplicant(user.getUserId());

        return new ApiResponse<>(true, "Dashboard retrieved", applications);
    }

    @PostMapping("/apply")
    public ApiResponse<PassportApplication> submitApplication(@RequestParam String fullName,
            @RequestParam Date dateOfBirth, @RequestParam String placeOfBirth,
            @RequestParam String currentAddress, @RequestParam String permanentAddress,
            @RequestParam ApplicationType applicationType,
            @RequestParam("identityProof") MultipartFile identityProof,
            @RequestParam("addressProof") MultipartFile addressProof, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ApiResponse<>(false, "Not authenticated");
        }

        try {
            PassportApplication application = applicationService.submitApplication(user, fullName,
                    dateOfBirth, placeOfBirth, currentAddress, permanentAddress, applicationType,
                    identityProof, addressProof);

            return new ApiResponse<>(true, "Application submitted successfully", application);
        } catch (IOException e) {
            return new ApiResponse<>(false, "Error uploading documents");
        }
    }

    @GetMapping("/track/{applicationId}")
    public ApiResponse<PassportApplication> trackApplication(@PathVariable int applicationId,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ApiResponse<>(false, "Not authenticated");
        }

        PassportApplication application = applicationService.getApplicationById(applicationId);

        if (application == null || application.getApplicant().getUserId() != user.getUserId()) {
            return new ApiResponse<>(false, "Application not found");
        }

        return new ApiResponse<>(true, "Application retrieved", application);
    }

    @GetMapping("/appointment/{applicationId}")
    public ApiResponse<PassportApplication> getAppointmentForm(@PathVariable int applicationId,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ApiResponse<>(false, "Not authenticated");
        }

        PassportApplication application = applicationService.getApplicationById(applicationId);

        if (application == null || application.getApplicant().getUserId() != user.getUserId()) {
            return new ApiResponse<>(false, "Application not found");
        }

        return new ApiResponse<>(true, "Application retrieved", application);
    }

    @PostMapping("/appointment/{applicationId}")
    public ApiResponse<Void> bookAppointment(@PathVariable int applicationId,
            @RequestParam Date appointmentDate, @RequestParam String appointmentTime,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ApiResponse<>(false, "Not authenticated");
        }

        applicationService.scheduleAppointment(applicationId, appointmentDate, appointmentTime);
        return new ApiResponse<>(true, "Appointment booked successfully");
    }
}
