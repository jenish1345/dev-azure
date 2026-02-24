package com.passport.automation.domain;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    
    private String name;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String mobileNumber;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    // Constructors
    public User() {
    }
    
    public User(String name, Date dateOfBirth, String address, String email, 
                String mobileNumber, String password, UserRole role) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
}
