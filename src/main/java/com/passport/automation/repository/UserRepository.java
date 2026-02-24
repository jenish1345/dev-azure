package com.passport.automation.repository;

import com.passport.automation.domain.User;
import com.passport.automation.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByEmailAndPassword(String email, String password);
    
    User findByEmail(String email);
    
    User findByMobileNumber(String mobileNumber);
    
    List<User> findByRole(UserRole role);
    
    boolean existsByEmail(String email);
}
