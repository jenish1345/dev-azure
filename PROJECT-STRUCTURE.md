# Passport Automation System - MVC Architecture Documentation

## Project Structure Overview

```
passport-automation-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/passport/automation/
│   │   │       │
│   │   │       ├── PassportAutomationApplication.java    # Main Spring Boot Application
│   │   │       │
│   │   │       ├── controller/                           # CONTROLLER LAYER (C in MVC)
│   │   │       │   ├── AuthController.java              # Handles login, registration, logout
│   │   │       │   ├── ApplicantController.java         # Applicant dashboard & operations
│   │   │       │   ├── OfficerController.java           # Passport officer operations
│   │   │       │   ├── PoliceController.java            # Police verification operations
│   │   │       │   └── AdminController.java             # Admin dashboard & user management
│   │   │       │
│   │   │       ├── domain/                               # MODEL LAYER (M in MVC)
│   │   │       │   ├── User.java                        # User entity
│   │   │       │   ├── PassportApplication.java         # Passport application entity
│   │   │       │   ├── UserRole.java                    # Enum for user roles
│   │   │       │   ├── ApplicationType.java             # Enum for application types
│   │   │       │   ├── ApplicationStatus.java           # Enum for application status
│   │   │       │   └── VerificationStatus.java          # Enum for verification status
│   │   │       │
│   │   │       ├── repository/                           # DATA ACCESS LAYER
│   │   │       │   ├── UserRepository.java              # User data operations
│   │   │       │   └── PassportApplicationRepository.java # Application data operations
│   │   │       │
│   │   │       ├── service/                              # SERVICE/BUSINESS LOGIC LAYER
│   │   │       │   ├── UserService.java                 # User business logic
│   │   │       │   └── PassportApplicationService.java  # Application business logic
│   │   │       │
│   │   │       └── scheduler/                            # SCHEDULED TASKS (Optional)
│   │   │           └── NotificationScheduler.java       # Email/SMS notifications
│   │   │
│   │   └── resources/
│   │       ├── templates/                                # VIEW LAYER (V in MVC)
│   │       │   ├── index.html                           # Landing page
│   │       │   ├── login.html                           # Login page
│   │       │   ├── register.html                        # Registration page
│   │       │   ├── applicant-dashboard.html             # Applicant dashboard
│   │       │   ├── application-form.html                # Application submission form
│   │       │   ├── track-application.html               # Application tracking
│   │       │   ├── book-appointment.html                # Appointment booking
│   │       │   ├── officer-dashboard.html               # Officer dashboard
│   │       │   ├── officer-view-application.html        # Officer application view
│   │       │   ├── police-dashboard.html                # Police dashboard
│   │       │   ├── police-verify.html                   # Police verification form
│   │       │   ├── admin-dashboard.html                 # Admin dashboard
│   │       │   ├── manage-users.html                    # User management
│   │       │   └── profile.html                         # User profile
│   │       │
│   │       ├── static/                                   # STATIC RESOURCES
│   │       │   ├── css/
│   │       │   │   └── styles.css                       # Custom CSS
│   │       │   ├── js/
│   │       │   │   └── main.js                          # Custom JavaScript
│   │       │   └── images/
│   │       │       └── logo.png                         # Application logo
│   │       │
│   │       └── application.properties                    # Application configuration
│   │
│   └── test/
│       └── java/
│           └── com/passport/automation/
│               ├── controller/                           # Controller tests
│               ├── service/                              # Service tests
│               └── repository/                           # Repository tests
│
├── uploads/                                              # File upload directory
├── pom.xml                                               # Maven configuration
├── database-schema.sql                                   # Database schema
├── README.md                                             # Project documentation
└── .gitignore                                            # Git ignore file
```

## MVC Architecture Breakdown

### 1. MODEL Layer (Domain)

**Location**: `src/main/java/com/passport/automation/domain/`

**Purpose**: Represents the data structure and business entities

**Components**:
- **User.java**: Represents system users
  - Fields: userId, name, email, password, role, dateOfBirth, address, mobileNumber
  - Roles: APPLICANT, PASSPORT_OFFICER, POLICE_VERIFICATION_OFFICER, ADMINISTRATOR

- **PassportApplication.java**: Represents passport applications
  - Fields: applicationId, applicant, fullName, currentAddress, permanentAddress, 
            applicationType, status, documents, appointmentDate, etc.
  - Relationships: ManyToOne with User

- **Enums**:
  - UserRole: APPLICANT, PASSPORT_OFFICER, POLICE_VERIFICATION_OFFICER, ADMINISTRATOR
  - ApplicationType: NEW_PASSPORT, RENEWAL, REISSUE, TATKAL
  - ApplicationStatus: SUBMITTED, UNDER_VERIFICATION, APPROVED, REJECTED
  - VerificationStatus: PENDING, VERIFIED, NOT_VERIFIED

### 2. VIEW Layer (Templates)

**Location**: `src/main/resources/templates/`

**Technology**: Thymeleaf + HTML + TailwindCSS

**Purpose**: Presents data to users and captures user input

**Key Views**:
- **Public Views**: index.html, login.html, register.html
- **Applicant Views**: applicant-dashboard.html, application-form.html, track-application.html
- **Officer Views**: officer-dashboard.html, officer-view-application.html
- **Police Views**: police-dashboard.html, police-verify.html
- **Admin Views**: admin-dashboard.html, manage-users.html
- **Common Views**: profile.html

### 3. CONTROLLER Layer

**Location**: `src/main/java/com/passport/automation/controller/`

**Purpose**: Handles HTTP requests, processes user input, and returns appropriate views

**Controllers**:

#### AuthController
- **Routes**: /, /login, /register, /logout, /profile
- **Functions**: User registration, authentication, session management

#### ApplicantController
- **Base Route**: /applicant/*
- **Key Routes**:
  - GET /applicant/dashboard - View applications
  - GET /applicant/apply - Show application form
  - POST /applicant/apply - Submit application
  - GET /applicant/track/{id} - Track application
  - POST /applicant/appointment/{id} - Book appointment

#### OfficerController
- **Base Route**: /officer/*
- **Key Routes**:
  - GET /officer/dashboard - View pending applications
  - GET /officer/view/{id} - View application details
  - POST /officer/approve/{id} - Approve application
  - POST /officer/reject/{id} - Reject application

#### PoliceController
- **Base Route**: /police/*
- **Key Routes**:
  - GET /police/dashboard - View pending verifications
  - GET /police/verify/{id} - View application for verification
  - POST /police/verify/{id} - Update verification status

#### AdminController
- **Base Route**: /admin/*
- **Key Routes**:
  - GET /admin/dashboard - View statistics
  - GET /admin/users - Manage users
  - GET /admin/applications - View all applications
  - GET /admin/reports - Generate reports

### 4. REPOSITORY Layer (Data Access)

**Location**: `src/main/java/com/passport/automation/repository/`

**Technology**: Spring Data JPA

**Purpose**: Handles database operations

**Repositories**:
- **UserRepository**: CRUD operations for users
  - findByEmailAndPassword()
  - findByEmail()
  - findByRole()
  - existsByEmail()

- **PassportApplicationRepository**: CRUD operations for applications
  - findByApplicant()
  - findByStatus()
  - findByPoliceVerificationStatus()
  - countByStatus()

### 5. SERVICE Layer (Business Logic)

**Location**: `src/main/java/com/passport/automation/service/`

**Purpose**: Contains business logic, validates data, orchestrates operations

**Services**:

#### UserService
- registerUser()
- authenticate()
- findByEmail()
- updateUser()
- getUsersByRole()

#### PassportApplicationService
- submitApplication()
- scheduleAppointment()
- updatePoliceVerification()
- approveApplication()
- rejectApplication()
- getApplicationsByApplicant()
- getApplicationsByStatus()

## Data Flow

### Example: Submitting a Passport Application

```
1. USER ACTION
   │
   ├── User fills application form (application-form.html)
   │
2. HTTP REQUEST
   │
   ├── POST /applicant/apply
   │
3. CONTROLLER
   │
   ├── ApplicantController.submitApplication()
   │   ├── Receives form data and files
   │   ├── Validates input
   │
4. SERVICE LAYER
   │
   ├── PassportApplicationService.submitApplication()
   │   ├── Creates PassportApplication entity
   │   ├── Saves uploaded files
   │   ├── Sets initial status
   │
5. REPOSITORY LAYER
   │
   ├── PassportApplicationRepository.save()
   │   ├── Persists to database
   │
6. RESPONSE
   │
   ├── Redirect to applicant-dashboard
   │
7. VIEW
   │
   └── Display updated dashboard with new application
```

## Database Schema

### Users Table
```sql
- user_id (PK)
- name
- date_of_birth
- address
- email (UNIQUE)
- mobile_number
- password
- role (ENUM)
- created_at
- updated_at
```

### Passport_Applications Table
```sql
- application_id (PK)
- user_id (FK → users)
- full_name
- date_of_birth
- place_of_birth
- current_address
- permanent_address
- application_type (ENUM)
- identity_proof_path
- address_proof_path
- status (ENUM)
- submission_date
- appointment_date
- appointment_time
- police_verification_status (ENUM)
- rejection_reason
- created_at
- updated_at
```

## Configuration Files

### application.properties
- Database connection settings
- JPA/Hibernate configuration
- Thymeleaf settings
- File upload configuration
- Email/SMS settings

### pom.xml
- Spring Boot dependencies
- Thymeleaf
- Spring Data JPA
- MySQL Connector
- Mail support
- Validation

## Security Features

1. **Password Storage**: Passwords stored as plain text (Should be encrypted with BCrypt in production)
2. **Session Management**: HttpSession for user authentication
3. **File Upload Validation**: File type and size restrictions
4. **Role-Based Access Control**: Different dashboards for different roles

## Best Practices Implemented

1. **Separation of Concerns**: Clear separation between layers
2. **Dependency Injection**: Using @Autowired
3. **Transaction Management**: @Transactional annotations
4. **RESTful URLs**: Meaningful and consistent URL patterns
5. **Error Handling**: Try-catch blocks and validation
6. **Responsive Design**: TailwindCSS for mobile-friendly UI

## Future Enhancements

1. Spring Security for authentication
2. BCrypt password encryption
3. JWT token-based authentication
4. Email/SMS OTP verification
5. Payment gateway integration
6. REST API endpoints
7. React/Angular frontend
8. Microservices architecture
9. Docker containerization
10. CI/CD pipeline

---

**Course**: CCS342 - DevOps Lab  
**Experiment**: 2 - Project Implementation using MVC and Spring Framework  
**Institution**: LICET, Chennai
