# Passport Automation System

A comprehensive web-based passport automation system built using Spring Boot MVC framework.

## Project Overview

The Passport Automation System enables citizens to apply for passports online and allows government authorities to verify, process, and approve applications digitally. The system reduces manual work, minimizes processing time, ensures secure handling of applicant data, and provides transparent status tracking to users.

## Features

### User Roles
1. **Applicant (Citizen)**
   - Register and login
   - Submit passport application
   - Upload documents (Identity Proof, Address Proof)
   - Book appointment for document verification
   - Track application status in real-time

2. **Passport Officer**
   - View and verify applications
   - Approve or reject applications
   - View all applications and statistics

3. **Police Verification Officer**
   - View assigned applications
   - Update verification status (Verified/Not Verified)

4. **Administrator**
   - Manage users and system data
   - View statistics and generate reports
   - Overall system management

### Core Functionalities
- User authentication with OTP verification support
- Online passport application form
- Document upload (Identity and Address Proof)
- Appointment scheduling
- Application status tracking (Submitted → Under Verification → Approved/Rejected)
- Police verification workflow
- Email/SMS notifications
- Dashboard for all user roles
- Report generation

## Technology Stack

- **Backend Framework**: Spring Boot 3.2.5
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript, TailwindCSS
- **Database**: MySQL
- **Build Tool**: Maven
- **Java Version**: 21
- **ORM**: Spring Data JPA (Hibernate)

## Project Structure

```
passport-automation-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/passport/automation/
│   │   │       ├── controller/          # MVC Controllers
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── ApplicantController.java
│   │   │       │   ├── OfficerController.java
│   │   │       │   ├── PoliceController.java
│   │   │       │   └── AdminController.java
│   │   │       ├── domain/              # Entity/Model classes
│   │   │       │   ├── User.java
│   │   │       │   ├── PassportApplication.java
│   │   │       │   ├── UserRole.java
│   │   │       │   ├── ApplicationType.java
│   │   │       │   ├── ApplicationStatus.java
│   │   │       │   └── VerificationStatus.java
│   │   │       ├── repository/          # JPA Repositories
│   │   │       │   ├── UserRepository.java
│   │   │       │   └── PassportApplicationRepository.java
│   │   │       ├── service/             # Business Logic
│   │   │       │   ├── UserService.java
│   │   │       │   └── PassportApplicationService.java
│   │   │       └── PassportAutomationApplication.java
│   │   └── resources/
│   │       ├── templates/               # Thymeleaf HTML templates
│   │       ├── static/                  # CSS, JS, Images
│   │       └── application.properties
│   └── test/
│       └── java/                        # Test cases
├── pom.xml                              # Maven configuration
└── README.md
```

## MVC Architecture

### Model (Domain Layer)
- **User**: Represents system users (Applicant, Officer, Police, Admin)
- **PassportApplication**: Represents passport application details
- **Enums**: UserRole, ApplicationType, ApplicationStatus, VerificationStatus

### View (Presentation Layer)
- Thymeleaf templates for server-side rendering
- TailwindCSS for responsive UI design
- HTML5, CSS3, JavaScript

### Controller (Control Layer)
- **AuthController**: Handles registration, login, logout
- **ApplicantController**: Manages applicant operations
- **OfficerController**: Manages passport officer operations
- **PoliceController**: Manages police verification
- **AdminController**: Manages admin operations

## Prerequisites

1. **Java Development Kit (JDK) 21** or higher
2. **Maven 3.6+**
3. **MySQL 8.0+**
4. **IDE**: IntelliJ IDEA / Eclipse / VS Code with Java extensions

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd passport-automation-system
```

### 2. Database Setup

#### Create Database
```sql
CREATE DATABASE passport_db;
USE passport_db;
```

The tables will be auto-created by Hibernate when you run the application (spring.jpa.hibernate.ddl-auto=update).

#### Manual Table Creation (Optional)
See `database-schema.sql` for complete schema.

### 3. Configure Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/passport_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Email Configuration (Optional - for notifications)
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

Or run from your IDE by executing the main class:
```
com.passport.automation.PassportAutomationApplication
```

### 6. Access the Application

Open your browser and navigate to:
```
http://localhost:8081
```

## Default Test Users

You can create test users through the registration page or insert them manually:

```sql
-- Applicant
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('John Doe', '1990-01-01', '123 Main St', 'applicant@test.com', '9876543210', 'password123', 'APPLICANT');

-- Passport Officer
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Officer Smith', '1985-05-15', '456 Govt Office', 'officer@test.com', '9876543211', 'officer123', 'PASSPORT_OFFICER');

-- Police Officer
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Inspector Kumar', '1987-08-20', '789 Police Station', 'police@test.com', '9876543212', 'police123', 'POLICE_VERIFICATION_OFFICER');

-- Administrator
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Admin User', '1980-12-10', '999 Admin Office', 'admin@test.com', '9876543213', 'admin123', 'ADMINISTRATOR');
```

## Application Workflow

1. **Registration**: Citizen registers with personal details
2. **Login**: User logs in with email and password
3. **Apply**: Applicant fills application form and uploads documents
4. **Appointment**: Applicant books appointment for document verification
5. **Police Verification**: Police officer verifies applicant details
6. **Officer Review**: Passport officer reviews and approves/rejects
7. **Status Tracking**: Applicant can track application status
8. **Admin Oversight**: Administrator monitors the entire process

## Application Status Flow

```
SUBMITTED → UNDER_VERIFICATION → APPROVED/REJECTED
```

## Non-Functional Requirements

- **Security**: Password encryption, secure file upload
- **Performance**: Fast response time, supports concurrent users
- **Availability**: 24/7 system availability
- **Usability**: User-friendly interface
- **Scalability**: Handles multiple users simultaneously

## Testing

Run tests using Maven:
```bash
mvn test
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check credentials in application.properties
   - Ensure database exists

2. **Port Already in Use**
   - Change port in application.properties: `server.port=8081`

3. **File Upload Error**
   - Check file size (max 10MB)
   - Verify upload directory permissions

## Future Enhancements

- SMS/Email OTP verification
- Payment gateway integration
- Passport printing and delivery tracking
- Multi-language support
- Mobile application
- Biometric integration
- Document scanner integration

## Contributors

- Student Name: KEERTHIVASAN S
- Roll Number: 311123205031
- Institution: LOYOLA-ICAM College of Engineering and Technology (LICET)

## License

This project is developed for educational purposes as part of the DevOps Lab coursework.

## Contact

For queries and support, please contact:
- Email: support@passportautomation.com
- Institution: LICET, Chennai

---

**Date**: 07/01/2026  
**Course**: CCS342 - DevOps Lab  
**Experiment No**: 2 - Project Implementation using MVC and Spring Framework
