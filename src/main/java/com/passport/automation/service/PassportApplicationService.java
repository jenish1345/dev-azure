package com.passport.automation.service;

import com.passport.automation.domain.*;
import com.passport.automation.repository.PassportApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class PassportApplicationService {
    
    @Autowired
    private PassportApplicationRepository applicationRepository;
    
    private static final String UPLOAD_DIR = "uploads/";
    
    @Transactional
    public PassportApplication submitApplication(User applicant, String fullName, 
                                                Date dateOfBirth, String placeOfBirth,
                                                String currentAddress, String permanentAddress,
                                                ApplicationType applicationType,
                                                MultipartFile identityProof, 
                                                MultipartFile addressProof) throws IOException {
        
        PassportApplication application = new PassportApplication(
            applicant, fullName, dateOfBirth, placeOfBirth, 
            currentAddress, permanentAddress, applicationType
        );
        
        // Save uploaded files
        if (identityProof != null && !identityProof.isEmpty()) {
            String identityProofPath = saveFile(identityProof);
            application.setIdentityProofPath(identityProofPath);
        }
        
        if (addressProof != null && !addressProof.isEmpty()) {
            String addressProofPath = saveFile(addressProof);
            application.setAddressProofPath(addressProofPath);
        }
        
        application.setPoliceVerificationStatus(VerificationStatus.PENDING);
        
        return applicationRepository.save(application);
    }
    
    private String saveFile(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // Generate unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        
        // Save file
        Files.write(filePath, file.getBytes());
        
        return fileName;
    }
    
    @Transactional
    public void scheduleAppointment(int applicationId, Date appointmentDate, String appointmentTime) {
        PassportApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setAppointmentDate(appointmentDate);
        application.setAppointmentTime(appointmentTime);
        applicationRepository.save(application);
    }
    
    @Transactional
    public void updatePoliceVerification(int applicationId, VerificationStatus status) {
        PassportApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setPoliceVerificationStatus(status);
        
        if (status == VerificationStatus.VERIFIED) {
            application.setStatus(ApplicationStatus.UNDER_VERIFICATION);
        } else if (status == VerificationStatus.NOT_VERIFIED) {
            application.setStatus(ApplicationStatus.REJECTED);
            application.setRejectionReason("Police verification failed");
        }
        
        applicationRepository.save(application);
    }
    
    @Transactional
    public void approveApplication(int applicationId) {
        PassportApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setStatus(ApplicationStatus.APPROVED);
        applicationRepository.save(application);
    }
    
    @Transactional
    public void rejectApplication(int applicationId, String reason) {
        PassportApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setStatus(ApplicationStatus.REJECTED);
        application.setRejectionReason(reason);
        applicationRepository.save(application);
    }
    
    public List<PassportApplication> getApplicationsByApplicant(int userId) {
        return applicationRepository.findByApplicantUserId(userId);
    }
    
    public List<PassportApplication> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public List<PassportApplication> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }
    
    public List<PassportApplication> getApplicationsForPoliceVerification() {
        return applicationRepository.findByPoliceVerificationStatus(VerificationStatus.PENDING);
    }
    
    public PassportApplication getApplicationById(int applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }
    
    public long getApplicationCountByStatus(ApplicationStatus status) {
        return applicationRepository.countByStatus(status);
    }
}
