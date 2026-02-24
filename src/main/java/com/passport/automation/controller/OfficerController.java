package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.service.PassportApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/officer")
public class OfficerController {
    
    @Autowired
    private PassportApplicationService applicationService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.PASSPORT_OFFICER) {
            return "redirect:/login";
        }
        
        List<PassportApplication> pendingApplications = 
            applicationService.getApplicationsByStatus(ApplicationStatus.UNDER_VERIFICATION);
        
        long submittedCount = applicationService.getApplicationCountByStatus(ApplicationStatus.SUBMITTED);
        long underVerificationCount = applicationService.getApplicationCountByStatus(ApplicationStatus.UNDER_VERIFICATION);
        long approvedCount = applicationService.getApplicationCountByStatus(ApplicationStatus.APPROVED);
        long rejectedCount = applicationService.getApplicationCountByStatus(ApplicationStatus.REJECTED);
        
        model.addAttribute("applications", pendingApplications);
        model.addAttribute("submittedCount", submittedCount);
        model.addAttribute("underVerificationCount", underVerificationCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("rejectedCount", rejectedCount);
        
        return "officer-dashboard";
    }
    
    @GetMapping("/view/{applicationId}")
    public String viewApplication(@PathVariable int applicationId,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.PASSPORT_OFFICER) {
            return "redirect:/login";
        }
        
        PassportApplication application = applicationService.getApplicationById(applicationId);
        model.addAttribute("application", application);
        return "officer-view-application";
    }
    
    @PostMapping("/approve/{applicationId}")
    public String approveApplication(@PathVariable int applicationId,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.PASSPORT_OFFICER) {
            return "redirect:/login";
        }
        
        applicationService.approveApplication(applicationId);
        return "redirect:/officer/dashboard";
    }
    
    @PostMapping("/reject/{applicationId}")
    public String rejectApplication(@PathVariable int applicationId,
                                   @RequestParam String reason,
                                   HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.PASSPORT_OFFICER) {
            return "redirect:/login";
        }
        
        applicationService.rejectApplication(applicationId, reason);
        return "redirect:/officer/dashboard";
    }
    
    @GetMapping("/all-applications")
    public String allApplications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.PASSPORT_OFFICER) {
            return "redirect:/login";
        }
        
        List<PassportApplication> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "all-applications";
    }
}
