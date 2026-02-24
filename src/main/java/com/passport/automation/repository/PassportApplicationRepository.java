package com.passport.automation.repository;

import com.passport.automation.domain.PassportApplication;
import com.passport.automation.domain.ApplicationStatus;
import com.passport.automation.domain.VerificationStatus;
import com.passport.automation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PassportApplicationRepository extends JpaRepository<PassportApplication, Integer> {
    
    List<PassportApplication> findByApplicant(User applicant);
    
    List<PassportApplication> findByStatus(ApplicationStatus status);
    
    List<PassportApplication> findByPoliceVerificationStatus(VerificationStatus status);
    
    List<PassportApplication> findByApplicantUserId(int userId);
    
    long countByStatus(ApplicationStatus status);
}
