# 📦 Complete Project Structure

## 🎯 Passport Automation System - Full File Tree

```
passport-automation-system/
│
├── 📁 .mvn/                                    # Maven Wrapper Configuration
│   └── wrapper/
│       └── maven-wrapper.properties           # Maven download config
│
├── 📁 .vscode/                                 # VS Code IDE Settings
│   ├── settings.json                          # Editor configuration
│   └── launch.json                            # Debug configuration
│
├── 📁 src/                                     # Source Code Root
│   │
│   ├── 📁 main/                                # Main Application Code
│   │   │
│   │   ├── 📁 java/                            # Java Source Files
│   │   │   └── com/passport/automation/
│   │   │       │
│   │   │       ├── ⭐ PassportAutomationApplication.java    # Main Spring Boot App
│   │   │       │
│   │   │       ├── 📋 controller/                          # MVC Controllers (5 files)
│   │   │       │   ├── AdminController.java               # Admin management
│   │   │       │   ├── ApplicantController.java           # Applicant operations
│   │   │       │   ├── AuthController.java                # Authentication
│   │   │       │   ├── OfficerController.java             # Officer operations
│   │   │       │   └── PoliceController.java              # Police verification
│   │   │       │
│   │   │       ├── 📦 domain/                              # Domain Models (6 files)
│   │   │       │   ├── ApplicationStatus.java             # Enum: SUBMITTED, APPROVED, etc.
│   │   │       │   ├── ApplicationType.java               # Enum: NEW_PASSPORT, RENEWAL, etc.
│   │   │       │   ├── PassportApplication.java           # Main application entity
│   │   │       │   ├── User.java                          # User entity
│   │   │       │   ├── UserRole.java                      # Enum: APPLICANT, OFFICER, etc.
│   │   │       │   └── VerificationStatus.java            # Enum: PENDING, VERIFIED, etc.
│   │   │       │
│   │   │       ├── 💾 repository/                         # Data Access Layer (2 files)
│   │   │       │   ├── PassportApplicationRepository.java # Application queries
│   │   │       │   └── UserRepository.java                # User queries
│   │   │       │
│   │   │       └── ⚙️ service/                             # Business Logic (2 files)
│   │   │           ├── PassportApplicationService.java    # Application operations
│   │   │           └── UserService.java                   # User operations
│   │   │
│   │   └── 📁 resources/                       # Configuration & Templates
│   │       │
│   │       ├── 📁 static/                      # Static Files (CSS, JS, Images)
│   │       │   ├── css/                        # Stylesheets
│   │       │   ├── js/                         # JavaScript files
│   │       │   └── images/                     # Images & icons
│   │       │
│   │       ├── 📁 templates/                   # Thymeleaf HTML Templates (6+ files)
│   │       │   ├── index.html                  # Landing page
│   │       │   ├── login.html                  # Login page
│   │       │   ├── register.html               # Registration page
│   │       │   ├── applicant-dashboard.html    # Applicant dashboard
│   │       │   ├── application-form.html       # Passport application form
│   │       │   └── track-application.html      # Application tracking
│   │       │
│   │       └── ⚙️ application.properties        # App configuration
│   │
│   └── 📁 test/                                # Test Files
│       └── java/
│           └── com/passport/automation/
│               └── (Test classes here)
│
├── 📁 target/                                  # Build Output (Generated)
│   ├── classes/                               # Compiled .class files
│   ├── test-classes/                          # Compiled test files
│   └── passport-automation-system-1.0.0.jar   # Executable JAR
│
├── 📁 uploads/                                 # User Uploaded Files
│   ├── identity-proofs/                       # Identity documents
│   └── address-proofs/                        # Address documents
│
├── 📄 .gitattributes                           # Git file attributes
├── 📄 .gitignore                               # Git ignore rules
├── 📄 HELP.md                                  # Spring Boot help & references
├── 📄 mvnw                                     # Maven wrapper script (Unix/Linux/Mac)
├── 📄 mvnw.cmd                                 # Maven wrapper script (Windows)
├── 📄 pom.xml                                  # Maven project configuration
├── 🗄️ database-schema.sql                      # Database setup script
│
├── 📚 Documentation Files:
├── 📄 README.md                                # Main project documentation
├── 📄 QUICK-START.md                           # Quick setup guide (5 min)
├── 📄 PROJECT_SETUP_GUIDE.md                   # Detailed setup instructions
├── 📄 PROJECT-STRUCTURE.md                     # Architecture explanation
└── 📄 PROJECT-SUMMARY.md                       # Project overview & metrics

```

---

## 📊 File Statistics

| Category | Count | Description |
|----------|-------|-------------|
| **Java Files** | 14 | Controllers, Services, Repositories, Entities |
| **HTML Templates** | 6+ | Thymeleaf templates for UI |
| **Configuration Files** | 5 | pom.xml, application.properties, etc. |
| **Documentation** | 6 | README, guides, structure docs |
| **Maven Wrapper** | 3 | Maven wrapper scripts & config |
| **IDE Config** | 3 | VS Code settings & launch config |
| **Database** | 1 | Complete schema with sample data |
| **Total Project Files** | 40+ | Complete working application |

---

## 🔍 Key Directories Explained

### 📁 `/src/main/java/` - Java Source Code
Contains all Java source files organized by layers:
- **Controller Layer**: Handles HTTP requests and responses
- **Domain Layer**: Entity models and enums
- **Repository Layer**: Database access interfaces
- **Service Layer**: Business logic implementation

### 📁 `/src/main/resources/` - Application Resources
- **templates/**: Thymeleaf HTML templates for the web UI
- **static/**: CSS, JavaScript, and image files
- **application.properties**: Database and app configuration

### 📁 `/src/test/java/` - Test Code
Unit tests and integration tests (to be implemented)

### 📁 `/target/` - Build Output
Generated by Maven during build process. Contains:
- Compiled `.class` files
- Packaged JAR file
- Test results

### 📁 `/uploads/` - File Storage
Runtime directory for user-uploaded documents
- Created automatically by the application
- Stores identity proofs and address proofs

---

## 🏗️ Architecture Layers

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (HTML Templates + Controllers)         │
│  - index.html, login.html, etc.         │
│  - AuthController, ApplicantController  │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Service Layer                  │
│      (Business Logic)                   │
│  - UserService                          │
│  - PassportApplicationService           │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│        Repository Layer                 │
│      (Data Access)                      │
│  - UserRepository                       │
│  - PassportApplicationRepository        │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Database Layer                 │
│         (MySQL)                         │
│  - users table                          │
│  - passport_applications table          │
└─────────────────────────────────────────┘
```

---

## 🎯 Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Backend** | Spring Boot | 3.2.5 |
| **Language** | Java | 21 |
| **Framework** | Spring MVC | 3.2.5 |
| **ORM** | Spring Data JPA (Hibernate) | 3.2.5 |
| **Database** | MySQL | 8.0+ |
| **Template Engine** | Thymeleaf | 3.2.5 |
| **Frontend** | HTML5 + TailwindCSS | 2.2.19 |
| **Build Tool** | Maven | 3.9.6 |
| **Server** | Tomcat (Embedded) | 10.1.x |

---

## 📝 Important Files

### Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies and build configuration |
| `application.properties` | Database connection, server port, JPA settings |
| `.gitignore` | Files to exclude from version control |
| `.gitattributes` | Git file handling attributes |

### Maven Wrapper Files

| File | Purpose |
|------|---------|
| `mvnw` | Maven wrapper for Unix/Linux/Mac |
| `mvnw.cmd` | Maven wrapper for Windows |
| `.mvn/wrapper/maven-wrapper.properties` | Maven download configuration |

### Documentation Files

| File | Purpose |
|------|---------|
| `README.md` | Main project documentation with features and setup |
| `QUICK-START.md` | Fast 5-minute setup guide |
| `PROJECT_SETUP_GUIDE.md` | Comprehensive setup instructions |
| `PROJECT-STRUCTURE.md` | Detailed architecture explanation |
| `PROJECT-SUMMARY.md` | Project overview, metrics, and learning outcomes |
| `HELP.md` | Spring Boot reference links |

### Database Files

| File | Purpose |
|------|---------|
| `database-schema.sql` | Complete schema with tables, views, procedures, sample data |

---

## 🚀 Quick Commands

### Build Project
```bash
mvn clean install
```

### Run Application
```bash
mvn spring-boot:run
```

### Using Maven Wrapper (Windows)
```cmd
mvnw.cmd spring-boot:run
```

### Using Maven Wrapper (Unix/Linux/Mac)
```bash
./mvnw spring-boot:run
```

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/passport-automation-system-1.0.0.jar
```

---

## 🌐 Application URLs

| URL | Description |
|-----|-------------|
| http://localhost:8080 | Landing page |
| http://localhost:8080/login | Login page |
| http://localhost:8080/register | Registration |
| http://localhost:8080/applicant/dashboard | Applicant dashboard |
| http://localhost:8080/officer/dashboard | Officer dashboard |
| http://localhost:8080/police/dashboard | Police dashboard |
| http://localhost:8080/admin/dashboard | Admin dashboard |

---

## 📦 Dependencies in pom.xml

### Core Spring Boot
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-thymeleaf

### Database
- mysql-connector-j

### Development
- spring-boot-devtools
- lombok (optional)

### Validation
- spring-boot-starter-validation

### Testing
- spring-boot-starter-test

---

## 🎓 Learning Outcomes

This project demonstrates:
- ✅ Spring Boot MVC architecture
- ✅ RESTful web application design
- ✅ Database design and JPA/Hibernate ORM
- ✅ Session-based authentication
- ✅ File upload handling
- ✅ Multi-role user management
- ✅ Thymeleaf template engine
- ✅ Maven project structure
- ✅ Git version control
- ✅ Professional documentation

---

## 📌 Notes

- The `target/` directory is auto-generated and should not be committed to Git
- The `uploads/` directory is created at runtime for file storage
- All Maven dependencies are automatically downloaded on first build
- The project uses embedded Tomcat server (no separate installation needed)

---

**Created by: KEERTHIVASAN S (311123205031)**
**Institution: LICET Chennai**
**Course: CCS342 DevOps Lab**

---

*Last Updated: February 2024*
*Version: 1.0.0*
