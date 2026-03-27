package com.passport.automation.controller;

import com.passport.automation.domain.User;
import com.passport.automation.domain.UserRole;
import com.passport.automation.dto.ApiResponse;
import com.passport.automation.dto.LoginRequest;
import com.passport.automation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        boolean authenticated = userService.authenticate(user);

        if (authenticated) {
            User completeUser = userService.findByEmail(request.getEmail());
            session.setAttribute("userId", completeUser.getUserId());
            session.setAttribute("user", completeUser);

            return new ApiResponse<>(true, "Login successful", completeUser);
        } else {
            return new ApiResponse<>(false, "Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestParam String name, @RequestParam Date dateOfBirth,
            @RequestParam String address, @RequestParam String email,
            @RequestParam String mobileNumber, @RequestParam String password, HttpSession session) {

        if (userService.emailExists(email)) {
            return new ApiResponse<>(false, "Email already registered");
        }

        userService.registerUser(name, dateOfBirth, address, email, mobileNumber, password,
                UserRole.APPLICANT);

        User user = userService.findByEmail(email);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("user", user);

        return new ApiResponse<>(true, "Registration successful", user);
    }

    @GetMapping("/profile")
    public ApiResponse<User> profile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ApiResponse<>(false, "Not authenticated");
        }
        return new ApiResponse<>(true, "Profile retrieved", user);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return new ApiResponse<>(true, "Logout successful");
    }

    @GetMapping("/verify")
    public ApiResponse<Boolean> verify(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return new ApiResponse<>(true, "Authenticated", true);
        }
        return new ApiResponse<>(false, "Not authenticated", false);
    }
}
