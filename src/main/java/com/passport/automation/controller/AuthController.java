package com.passport.automation.controller;

import com.passport.automation.domain.User;
import com.passport.automation.domain.UserRole;
import com.passport.automation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String name,
                          @RequestParam Date dateOfBirth,
                          @RequestParam String address,
                          @RequestParam String email,
                          @RequestParam String mobileNumber,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        
        if (userService.emailExists(email)) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        
        userService.registerUser(name, dateOfBirth, address, email, 
                               mobileNumber, password, UserRole.APPLICANT);
        
        User user = userService.findByEmail(email);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("user", user);
        
        return "redirect:/applicant/dashboard";
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        
        boolean authenticated = userService.authenticate(user);
        
        if (authenticated) {
            User completeUser = userService.findByEmail(email);
            session.setAttribute("userId", completeUser.getUserId());
            session.setAttribute("user", completeUser);
            
            // Redirect based on role
            switch (completeUser.getRole()) {
                case APPLICANT:
                    return "redirect:/applicant/dashboard";
                case PASSPORT_OFFICER:
                    return "redirect:/officer/dashboard";
                case POLICE_VERIFICATION_OFFICER:
                    return "redirect:/police/dashboard";
                case ADMINISTRATOR:
                    return "redirect:/admin/dashboard";
                default:
                    return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }
}
