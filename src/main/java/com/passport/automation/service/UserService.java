package com.passport.automation.service;

import com.passport.automation.domain.User;
import com.passport.automation.domain.UserRole;
import com.passport.automation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void registerUser(String name, Date dateOfBirth, String address, 
                           String email, String mobileNumber, String password, 
                           UserRole role) {
        User user = new User();
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address);
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        user.setPassword(password);
        user.setRole(role);
        
        userRepository.save(user);
    }
    
    public boolean authenticate(User user) {
        User storedUser = userRepository.findByEmail(user.getEmail());
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User findById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
