package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.service.PassportApplicationService;
import com.passport.automation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    
    @Autowired
    private PassportApplicationService applicationService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<PassportApplication> applications = 
            applicationService.getApplicationsByApplicant(user.getUserId());
        
        model.addAttribute("applications", applications);
        return "applicant-dashboard";
    }
    
    @GetMapping("/apply")
    public String showApplicationForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "application-form";
    }
    
    @PostMapping("/apply")
    public String submitApplication(@RequestParam String fullName,
                                   @RequestParam Date dateOfBirth,
                                   @RequestParam String placeOfBirth,
                                   @RequestParam String currentAddress,
                                   @RequestParam String permanentAddress,
                                   @RequestParam ApplicationType applicationType,
                                   @RequestParam("identityProof") MultipartFile identityProof,
                                   @RequestParam("addressProof") MultipartFile addressProof,
                                   HttpSession session,
                                   Model model) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        try {
            applicationService.submitApplication(user, fullName, dateOfBirth, 
                placeOfBirth, currentAddress, permanentAddress, applicationType,
                identityProof, addressProof);
            
            model.addAttribute("success", "Application submitted successfully");
            return "redirect:/applicant/dashboard";
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading documents");
            return "application-form";
        }
    }
    
    @GetMapping("/track/{applicationId}")
    public String trackApplication(@PathVariable int applicationId, 
                                  HttpSession session, 
                                  Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        PassportApplication application = applicationService.getApplicationById(applicationId);
        
        if (application == null || application.getApplicant().getUserId() != user.getUserId()) {
            return "redirect:/applicant/dashboard";
        }
        
        model.addAttribute("application", application);
        return "track-application";
    }
    
    @GetMapping("/appointment/{applicationId}")
    public String showAppointmentForm(@PathVariable int applicationId,
                                     HttpSession session,
                                     Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        PassportApplication application = applicationService.getApplicationById(applicationId);
        model.addAttribute("application", application);
        return "book-appointment";
    }
    
    @PostMapping("/appointment/{applicationId}")
    public String bookAppointment(@PathVariable int applicationId,
                                 @RequestParam Date appointmentDate,
                                 @RequestParam String appointmentTime,
                                 HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        applicationService.scheduleAppointment(applicationId, appointmentDate, appointmentTime);
        return "redirect:/applicant/dashboard";
    }
}
