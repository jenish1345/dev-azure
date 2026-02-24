# Passport Automation System - Project Summary

## 📋 Project Information

**Student Name**: KEERTHIVASAN S  
**Roll Number**: 311123205031  
**Institution**: LOYOLA-ICAM College of Engineering and Technology (LICET)  
**Course**: CCS342 - DevOps Lab  
**Experiment No**: 2  
**Experiment Title**: Project Implementation using MVC and Spring Framework  
**Date**: 07/01/2026

---

## 🎯 Project Overview

The **Passport Automation System** is a comprehensive web-based application that automates the entire passport issuance process. It enables citizens to apply for passports online and allows government authorities (Passport Officers, Police Verification Officers, and Administrators) to verify, process, and approve applications digitally.

### Key Objectives
✅ Reduce manual work in passport processing  
✅ Minimize processing time  
✅ Ensure secure handling of applicant data  
✅ Provide transparent status tracking to users  
✅ Implement complete MVC architecture using Spring Framework

---

## 🏗️ Architecture: MVC Pattern

### Model (Domain Layer)
**Location**: `src/main/java/com/passport/automation/domain/`

**Entities**:
1. **User** - Represents all system users
   - Attributes: userId, name, email, password, role, dateOfBirth, address, mobileNumber
   - Roles: APPLICANT, PASSPORT_OFFICER, POLICE_VERIFICATION_OFFICER, ADMINISTRATOR

2. **PassportApplication** - Represents passport applications
   - Attributes: applicationId, applicant, fullName, addresses, documents, status, etc.
   - Relationships: ManyToOne with User entity

**Enumerations**:
- UserRole, ApplicationType, ApplicationStatus, VerificationStatus

### View (Presentation Layer)
**Location**: `src/main/resources/templates/`

**Technology**: Thymeleaf Template Engine + TailwindCSS

**Key Views**:
- Public: index.html, login.html, register.html
- Applicant: applicant-dashboard.html, application-form.html, track-application.html
- Officer: officer-dashboard.html, officer-view-application.html
- Police: police-dashboard.html, police-verify.html
- Admin: admin-dashboard.html, manage-users.html

### Controller (Control Layer)
**Location**: `src/main/java/com/passport/automation/controller/`

**Controllers**:
1. **AuthController** - Authentication and registration
2. **ApplicantController** - Applicant operations
3. **OfficerController** - Passport officer operations
4. **PoliceController** - Police verification
5. **AdminController** - Administrative functions

### Additional Layers

**Repository Layer** (Data Access):
- UserRepository
- PassportApplicationRepository
- Uses Spring Data JPA for database operations

**Service Layer** (Business Logic):
- UserService
- PassportApplicationService
- Contains core business logic and validation

---

## 🛠️ Technology Stack

| Layer | Technology |
|-------|-----------|
| Backend Framework | Spring Boot 3.2.5 |
| Frontend | Thymeleaf, HTML5, CSS3, JavaScript |
| CSS Framework | TailwindCSS 2.2.19 |
| Database | MySQL 8.0+ |
| ORM | Spring Data JPA (Hibernate) |
| Build Tool | Maven |
| Java Version | 21 |
| Template Engine | Thymeleaf |

---

## 👥 User Roles & Features

### 1. Applicant (Citizen)
✅ Register and login to system  
✅ Submit passport application online  
✅ Upload identity and address proofs  
✅ Book appointment for document verification  
✅ Track application status in real-time  
✅ View application history

### 2. Passport Officer
✅ View pending applications  
✅ Review application details and documents  
✅ Approve or reject applications  
✅ View application statistics  
✅ Access complete application database

### 3. Police Verification Officer
✅ View assigned applications for verification  
✅ Access applicant information  
✅ Update verification status (Verified/Not Verified)  
✅ View verification history

### 4. Administrator
✅ Manage all users in the system  
✅ View comprehensive statistics  
✅ Access all applications  
✅ Generate reports  
✅ System configuration and management

---

## 📊 Application Workflow

```
1. APPLICANT REGISTERS
   ↓
2. APPLICANT LOGS IN
   ↓
3. FILLS APPLICATION FORM
   ↓
4. UPLOADS DOCUMENTS
   ↓
5. SUBMITS APPLICATION (Status: SUBMITTED)
   ↓
6. BOOKS APPOINTMENT (Optional)
   ↓
7. POLICE VERIFICATION (Status: UNDER_VERIFICATION)
   ↓
8. OFFICER REVIEW
   ↓
9. APPROVED or REJECTED
```

### Status Flow
- **SUBMITTED** → Initial status after application submission
- **UNDER_VERIFICATION** → After police verification
- **APPROVED** → Application approved by officer
- **REJECTED** → Application rejected by officer

---

## 🗄️ Database Design

### Tables

#### users
```sql
- user_id (Primary Key)
- name
- date_of_birth
- address
- email (Unique)
- mobile_number
- password
- role (ENUM)
```

#### passport_applications
```sql
- application_id (Primary Key)
- user_id (Foreign Key)
- full_name
- date_of_birth
- place_of_birth
- current_address
- permanent_address
- application_type
- identity_proof_path
- address_proof_path
- status
- submission_date
- appointment_date
- police_verification_status
- rejection_reason
```

---

## 📁 Project Structure

```
passport-automation-system/
├── src/
│   ├── main/
│   │   ├── java/com/passport/automation/
│   │   │   ├── PassportAutomationApplication.java
│   │   │   ├── controller/     (5 controllers)
│   │   │   ├── domain/         (6 entities/enums)
│   │   │   ├── repository/     (2 repositories)
│   │   │   └── service/        (2 services)
│   │   └── resources/
│   │       ├── templates/      (15+ HTML files)
│   │       ├── static/
│   │       └── application.properties
│   └── test/java/
├── pom.xml
├── database-schema.sql
├── README.md
├── PROJECT-STRUCTURE.md
├── QUICK-START.md
└── .gitignore
```

---

## ✨ Key Features Implemented

### Functional Requirements
✅ User authentication (Login/Register)  
✅ Role-based access control  
✅ Passport application management  
✅ Document upload functionality  
✅ Appointment scheduling  
✅ Police verification module  
✅ Application status tracking  
✅ Admin dashboard with statistics  
✅ Officer approval/rejection workflow

### Non-Functional Requirements
✅ User-friendly interface  
✅ Responsive design (TailwindCSS)  
✅ Secure file upload  
✅ Session management  
✅ Fast response time  
✅ Multiple concurrent users support  
✅ 24/7 availability

---

## 🔒 Security Features

1. **Authentication**: Email and password-based login
2. **Authorization**: Role-based access control
3. **Session Management**: HttpSession for user state
4. **File Upload Security**: File type and size validation
5. **Data Validation**: Input validation on forms

---

## 🚀 How to Run

### Prerequisites
- Java 21+
- MySQL 8.0+
- Maven 3.6+

### Setup Steps
```bash
# 1. Create database
mysql -u root -p
CREATE DATABASE passport_db;

# 2. Configure application.properties
# Edit database credentials

# 3. Run application
mvn spring-boot:run

# 4. Access application
http://localhost:8080
```

---

## 📊 Testing Scenarios

### Test Users
| Role | Email | Password |
|------|-------|----------|
| Applicant | applicant@test.com | test123 |
| Officer | officer@test.com | test123 |
| Police | police@test.com | test123 |
| Admin | admin@test.com | test123 |

### Test Cases Covered
1. ✅ User Registration
2. ✅ User Login
3. ✅ Passport Application Submission
4. ✅ Document Upload
5. ✅ Appointment Booking
6. ✅ Status Tracking
7. ✅ Police Verification
8. ✅ Officer Approval/Rejection
9. ✅ Admin User Management
10. ✅ Dashboard Statistics

---

## 📈 Statistics

### Project Metrics
- **Total Files**: 35+
- **Lines of Code**: ~3,500+
- **Controllers**: 5
- **Services**: 2
- **Repositories**: 2
- **Entities**: 6
- **HTML Templates**: 15+
- **Database Tables**: 2

---

## 🎓 Learning Outcomes

### Technical Skills Gained
1. ✅ Spring Boot framework
2. ✅ MVC architecture pattern
3. ✅ Spring Data JPA
4. ✅ Thymeleaf template engine
5. ✅ RESTful web services
6. ✅ Database design
7. ✅ Maven build tool
8. ✅ Git version control

### Concepts Implemented
1. ✅ Separation of concerns
2. ✅ Dependency injection
3. ✅ ORM mapping
4. ✅ Transaction management
5. ✅ File upload handling
6. ✅ Session management
7. ✅ Form validation
8. ✅ Responsive UI design

---

## 🔄 Future Enhancements

1. **Security**
   - Spring Security integration
   - BCrypt password encryption
   - JWT authentication
   - CSRF protection

2. **Features**
   - Email/SMS notifications
   - Payment gateway integration
   - Document scanner integration
   - Multi-language support
   - Mobile application

3. **Architecture**
   - REST API development
   - Microservices architecture
   - Docker containerization
   - CI/CD pipeline
   - Cloud deployment (AWS/Azure)

---

## 📝 Conclusion

The Passport Automation System successfully demonstrates the implementation of a complete MVC architecture using Spring Boot framework. The project covers all aspects of the Software Requirement Specification including:

- ✅ All four user roles implemented
- ✅ Complete application workflow
- ✅ Secure document handling
- ✅ Real-time status tracking
- ✅ Professional UI/UX design
- ✅ Scalable architecture

The system effectively reduces manual work, improves processing efficiency, and provides a transparent platform for passport application management.

---

## 🙏 Acknowledgments

This project was developed as part of the CCS342 DevOps Lab course at LOYOLA-ICAM College of Engineering and Technology (LICET), Chennai.

**Special thanks to**:
- Faculty members for guidance
- Reference materials and documentation
- Spring Boot and Java communities

---

## 📞 Contact Information

**Student**: KEERTHIVASAN S  
**Roll Number**: 311123205031  
**Institution**: LICET, Chennai  
**Department**: Information Technology  
**Academic Year**: 2025-2026

---

**Project Completion Date**: January 7, 2026  
**Status**: ✅ Completed Successfully

---

*This project demonstrates the practical application of MVC design pattern and Spring Framework in developing enterprise-level web applications.*
