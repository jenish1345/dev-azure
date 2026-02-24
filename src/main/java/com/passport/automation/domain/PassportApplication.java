package com.passport.automation.domain;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "passport_applications")
public class PassportApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User applicant;
    
    private String fullName;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String currentAddress;
    private String permanentAddress;
    
    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;
    
    private String identityProofPath;
    private String addressProofPath;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    private Timestamp submissionDate;
    private Date appointmentDate;
    private String appointmentTime;
    
    @Enumerated(EnumType.STRING)
    private VerificationStatus policeVerificationStatus;
    
    private String rejectionReason;
    
    // Constructors
    public PassportApplication() {
    }
    
    public PassportApplication(User applicant, String fullName, Date dateOfBirth, 
                              String placeOfBirth, String currentAddress, 
                              String permanentAddress, ApplicationType applicationType) {
        this.applicant = applicant;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.applicationType = applicationType;
        this.status = ApplicationStatus.SUBMITTED;
        this.submissionDate = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
    
    public User getApplicant() {
        return applicant;
    }
    
    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
    
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
    
    public String getCurrentAddress() {
        return currentAddress;
    }
    
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
    
    public String getPermanentAddress() {
        return permanentAddress;
    }
    
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
    
    public ApplicationType getApplicationType() {
        return applicationType;
    }
    
    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }
    
    public String getIdentityProofPath() {
        return identityProofPath;
    }
    
    public void setIdentityProofPath(String identityProofPath) {
        this.identityProofPath = identityProofPath;
    }
    
    public String getAddressProofPath() {
        return addressProofPath;
    }
    
    public void setAddressProofPath(String addressProofPath) {
        this.addressProofPath = addressProofPath;
    }
    
    public ApplicationStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    public Timestamp getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    public Date getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public String getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public VerificationStatus getPoliceVerificationStatus() {
        return policeVerificationStatus;
    }
    
    public void setPoliceVerificationStatus(VerificationStatus policeVerificationStatus) {
        this.policeVerificationStatus = policeVerificationStatus;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }
    
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
