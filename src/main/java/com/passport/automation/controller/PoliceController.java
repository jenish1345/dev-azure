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
@RequestMapping("/police")
public class PoliceController {
    
    @Autowired
    private PassportApplicationService applicationService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.POLICE_VERIFICATION_OFFICER) {
            return "redirect:/login";
        }
        
        List<PassportApplication> pendingVerifications = 
            applicationService.getApplicationsForPoliceVerification();
        
        model.addAttribute("applications", pendingVerifications);
        return "police-dashboard";
    }
    
    @GetMapping("/verify/{applicationId}")
    public String viewApplicationForVerification(@PathVariable int applicationId,
                                                HttpSession session,
                                                Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.POLICE_VERIFICATION_OFFICER) {
            return "redirect:/login";
        }
        
        PassportApplication application = applicationService.getApplicationById(applicationId);
        model.addAttribute("application", application);
        return "police-verify";
    }
    
    @PostMapping("/verify/{applicationId}")
    public String updateVerificationStatus(@PathVariable int applicationId,
                                          @RequestParam VerificationStatus status,
                                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.POLICE_VERIFICATION_OFFICER) {
            return "redirect:/login";
        }
        
        applicationService.updatePoliceVerification(applicationId, status);
        return "redirect:/police/dashboard";
    }
}
