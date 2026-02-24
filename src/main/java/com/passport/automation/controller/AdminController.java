package com.passport.automation.controller;

import com.passport.automation.domain.*;
import com.passport.automation.service.PassportApplicationService;
import com.passport.automation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private PassportApplicationService applicationService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.ADMINISTRATOR) {
            return "redirect:/login";
        }
        
        long totalApplications = applicationService.getAllApplications().size();
        long submittedCount = applicationService.getApplicationCountByStatus(ApplicationStatus.SUBMITTED);
        long underVerificationCount = applicationService.getApplicationCountByStatus(ApplicationStatus.UNDER_VERIFICATION);
        long approvedCount = applicationService.getApplicationCountByStatus(ApplicationStatus.APPROVED);
        long rejectedCount = applicationService.getApplicationCountByStatus(ApplicationStatus.REJECTED);
        
        model.addAttribute("totalApplications", totalApplications);
        model.addAttribute("submittedCount", submittedCount);
        model.addAttribute("underVerificationCount", underVerificationCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("rejectedCount", rejectedCount);
        
        return "admin-dashboard";
    }
    
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.ADMINISTRATOR) {
            return "redirect:/login";
        }
        
        List<User> applicants = userService.getUsersByRole(UserRole.APPLICANT);
        List<User> officers = userService.getUsersByRole(UserRole.PASSPORT_OFFICER);
        List<User> policeOfficers = userService.getUsersByRole(UserRole.POLICE_VERIFICATION_OFFICER);
        
        model.addAttribute("applicants", applicants);
        model.addAttribute("officers", officers);
        model.addAttribute("policeOfficers", policeOfficers);
        
        return "manage-users";
    }
    
    @GetMapping("/applications")
    public String viewAllApplications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.ADMINISTRATOR) {
            return "redirect:/login";
        }
        
        List<PassportApplication> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "admin-applications";
    }
    
    @GetMapping("/reports")
    public String generateReports(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.ADMINISTRATOR) {
            return "redirect:/login";
        }
        
        // Generate report data
        List<PassportApplication> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        
        return "reports";
    }
}
