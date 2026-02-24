# 🚀 Passport Automation System - Complete Setup Guide

## 📋 Table of Contents
1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [Database Configuration](#database-configuration)
4. [Running the Application](#running-the-application)
5. [Testing the System](#testing-the-system)
6. [Troubleshooting](#troubleshooting)
7. [Project Structure](#project-structure)

---

## 🔧 Prerequisites

Before you begin, ensure you have the following installed:

### Required Software:
- ☕ **Java Development Kit (JDK) 21 or higher**
  - Download from: https://www.oracle.com/java/technologies/downloads/
  - Verify: `java --version`
  
- 🔨 **Apache Maven 3.6+ or higher**
  - Download from: https://maven.apache.org/download.cgi
  - Verify: `mvn --version`
  - *Note: You can also use the included Maven wrapper (mvnw/mvnw.cmd)*
  
- 🗄️ **MySQL Server 8.0 or higher**
  - Download from: https://dev.mysql.com/downloads/mysql/
  - Verify: `mysql --version`

- 💻 **IDE (Choose one):**
  - Visual Studio Code (with Java Extension Pack)
  - IntelliJ IDEA
  - Eclipse IDE
  - Spring Tool Suite (STS)

---

## 📦 Project Setup

### Option 1: Using Maven Wrapper (Recommended)

The project includes Maven wrapper scripts, so you don't need Maven installed separately.

#### On Windows:
```cmd
mvnw.cmd clean install
```

#### On Unix/Linux/Mac:
```bash
./mvnw clean install
```

### Option 2: Using Installed Maven

```bash
mvn clean install
```

This will:
- Download all required dependencies
- Compile the source code
- Run tests (if any)
- Package the application

---

## 🗄️ Database Configuration

### Step 1: Create Database

Open MySQL Workbench or MySQL Command Line and run:

```sql
-- Create the database
CREATE DATABASE passport_db;

-- Verify it was created
SHOW DATABASES;

-- Use the database
USE passport_db;
```

### Step 2: Run Database Schema

Execute the provided SQL script:

```sql
-- Run this file:
SOURCE database-schema.sql;

-- OR copy and paste the contents of database-schema.sql
```

This will create:
- `users` table
- `passport_applications` table
- Sample test users for all roles
- Views for statistics
- Stored procedures

### Step 3: Configure Application Properties

Open `src/main/resources/application.properties` and update:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/passport_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Update these with your MySQL credentials
```

### Step 4: Verify Connection

The application will automatically create/update tables on startup if configured with:
```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ Running the Application

### Method 1: Using Maven

```bash
mvn spring-boot:run
```

### Method 2: Using Maven Wrapper

#### Windows:
```cmd
mvnw.cmd spring-boot:run
```

#### Unix/Linux/Mac:
```bash
./mvnw spring-boot:run
```

### Method 3: Using IDE

#### VS Code:
1. Open the project folder
2. Go to **Spring Boot Dashboard** (in the sidebar)
3. Click the ▶️ play button next to "passport-automation-system"

#### IntelliJ IDEA:
1. Right-click on `PassportAutomationApplication.java`
2. Select **"Run 'PassportAutomationApplication'"**

#### Eclipse/STS:
1. Right-click on the project
2. Select **Run As → Spring Boot App**

### Method 4: Running JAR File

```bash
# Build the JAR
mvn clean package

# Run the JAR
java -jar target/passport-automation-system-1.0.0.jar
```

---

## ✅ Testing the System

### Step 1: Access the Application

Open your web browser and navigate to:
```
http://localhost:8080
```

You should see the **Passport Automation System** landing page.

### Step 2: Test with Pre-configured Users

The `database-schema.sql` script creates test users for all roles:

| Role | Email | Password |
|------|-------|----------|
| **Applicant** | applicant@test.com | test123 |
| **Passport Officer** | officer@test.com | test123 |
| **Police Verification Officer** | police@test.com | test123 |
| **Administrator** | admin@test.com | test123 |

### Step 3: Test User Workflows

#### As Applicant:
1. ✅ Login with: `applicant@test.com` / `test123`
2. ✅ Navigate to **"Apply for Passport"**
3. ✅ Fill out the application form
4. ✅ Upload identity proof (PDF/Image, max 10MB)
5. ✅ Upload address proof (PDF/Image, max 10MB)
6. ✅ Submit the application
7. ✅ View application in **"My Applications"**
8. ✅ Track application status
9. ✅ Book appointment (after submission)

#### As Passport Officer:
1. ✅ Login with: `officer@test.com` / `test123`
2. ✅ View dashboard with statistics
3. ✅ See all pending applications
4. ✅ Click **"View"** on any application
5. ✅ Review application details
6. ✅ Approve or Reject with comments
7. ✅ Check **"All Applications"** page

#### As Police Verification Officer:
1. ✅ Login with: `police@test.com` / `test123`
2. ✅ View applications pending verification
3. ✅ Click **"Verify"** on any application
4. ✅ Mark as VERIFIED or NOT_VERIFIED
5. ✅ Add verification comments
6. ✅ Submit verification

#### As Administrator:
1. ✅ Login with: `admin@test.com` / `admin123`
2. ✅ View system statistics
3. ✅ Manage all users
4. ✅ View all applications
5. ✅ Generate reports
6. ✅ Monitor system activity

### Step 4: Test Complete Workflow

1. **Register** a new user (as Applicant)
2. **Login** with the new account
3. **Apply** for a passport with all required documents
4. **Logout** and login as Police Officer
5. **Verify** the application
6. **Logout** and login as Passport Officer
7. **Approve** the application
8. **Logout** and login back as the Applicant
9. **Track** the application status (should be APPROVED)

---

## 🐛 Troubleshooting

### Issue 1: Port 8080 Already in Use

**Error:** `Port 8080 is already in use`

**Solution:**

Option A: Change the port in `application.properties`:
```properties
server.port=8081
```

Option B: Stop the process using port 8080:
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Issue 2: Database Connection Failed

**Error:** `Cannot create PoolableConnectionFactory`

**Solution:**
1. ✅ Verify MySQL is running
2. ✅ Check database exists: `SHOW DATABASES;`
3. ✅ Verify credentials in `application.properties`
4. ✅ Test connection:
   ```bash
   mysql -u root -p
   ```

### Issue 3: Maven Dependencies Not Downloaded

**Error:** `Could not resolve dependencies`

**Solution:**
```bash
# Clear Maven cache
mvn dependency:purge-local-repository

# Re-download dependencies
mvn clean install -U
```

### Issue 4: Java Version Mismatch

**Error:** `Unsupported class file major version`

**Solution:**
1. Check Java version: `java --version`
2. Ensure JDK 21 or higher is installed
3. Update `JAVA_HOME` environment variable
4. Update in `pom.xml` if needed:
   ```xml
   <properties>
       <java.version>21</java.version>
   </properties>
   ```

### Issue 5: File Upload Not Working

**Error:** `FileNotFoundException` or upload fails

**Solution:**
1. Create `uploads` directory in project root:
   ```bash
   mkdir uploads
   ```
2. Verify file size limits in `application.properties`:
   ```properties
   spring.servlet.multipart.max-file-size=10MB
   spring.servlet.multipart.max-request-size=10MB
   ```

### Issue 6: Thymeleaf Template Not Found

**Error:** `TemplateInputException`

**Solution:**
1. ✅ Verify templates are in: `src/main/resources/templates/`
2. ✅ Check file names match controller return values
3. ✅ Ensure `.html` extension is present

### Issue 7: Static Resources Not Loading

**Error:** CSS/JS files not loading

**Solution:**
1. ✅ Place files in: `src/main/resources/static/`
2. ✅ Reference in HTML as: `/css/style.css`
3. ✅ Clear browser cache (Ctrl+F5)

---

## 📁 Project Structure

```
passport-automation-system/
├── .mvn/                           # Maven wrapper files
│   └── wrapper/
│       └── maven-wrapper.properties
├── .vscode/                        # VS Code settings
│   ├── settings.json
│   └── launch.json
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/passport/automation/
│   │   │       ├── PassportAutomationApplication.java ⭐
│   │   │       ├── controller/     # 5 Controllers
│   │   │       │   ├── AdminController.java
│   │   │       │   ├── ApplicantController.java
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── OfficerController.java
│   │   │       │   └── PoliceController.java
│   │   │       ├── domain/         # 6 Domain Classes
│   │   │       │   ├── ApplicationStatus.java
│   │   │       │   ├── ApplicationType.java
│   │   │       │   ├── PassportApplication.java
│   │   │       │   ├── User.java
│   │   │       │   ├── UserRole.java
│   │   │       │   └── VerificationStatus.java
│   │   │       ├── repository/     # 2 Repositories
│   │   │       │   ├── PassportApplicationRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       └── service/        # 2 Services
│   │   │           ├── PassportApplicationService.java
│   │   │           └── UserService.java
│   │   └── resources/
│   │       ├── static/             # CSS, JS, Images
│   │       ├── templates/          # 15+ HTML Templates
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── applicant-dashboard.html
│   │       │   ├── application-form.html
│   │       │   └── ... (more templates)
│   │       └── application.properties  # Configuration
│   └── test/
│       └── java/                   # Test files
├── target/                         # Build output
├── uploads/                        # User uploaded files
├── .gitattributes                  # Git attributes
├── .gitignore                      # Git ignore rules
├── HELP.md                         # Spring Boot help
├── mvnw                            # Maven wrapper (Unix)
├── mvnw.cmd                        # Maven wrapper (Windows)
├── pom.xml                         # Maven dependencies
├── database-schema.sql             # Database setup script
├── PROJECT_SETUP_GUIDE.md          # This file
├── PROJECT_STRUCTURE.md            # Architecture details
└── README.md                       # Project documentation
```

---

## 📊 Key Application URLs

| URL | Description |
|-----|-------------|
| http://localhost:8080 | Landing page |
| http://localhost:8080/login | Login page |
| http://localhost:8080/register | Registration page |
| http://localhost:8080/applicant/dashboard | Applicant dashboard |
| http://localhost:8080/officer/dashboard | Officer dashboard |
| http://localhost:8080/police/dashboard | Police dashboard |
| http://localhost:8080/admin/dashboard | Admin dashboard |

---

## 🔑 Important Configuration Properties

### Database Settings
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/passport_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### JPA/Hibernate Settings
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### File Upload Settings
```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### Thymeleaf Settings
```properties
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

---

## 📚 Additional Resources

- **README.md** - Comprehensive project documentation
- **QUICK-START.md** - 5-minute quick start guide
- **PROJECT-STRUCTURE.md** - Detailed architecture explanation
- **PROJECT-SUMMARY.md** - Project overview and metrics
- **database-schema.sql** - Complete database setup with sample data

---

## 🎯 Next Steps

1. ✅ Complete all setup steps above
2. ✅ Test each user workflow
3. ✅ Explore the code structure
4. ✅ Customize as needed for your requirements
5. ✅ Add additional features (see README.md for ideas)

---

## 💡 Tips

- Use **Spring Boot DevTools** for hot reload during development
- Check application logs for debugging information
- Use MySQL Workbench for easier database management
- Test file uploads with small files first
- Keep backup of database before major changes

---

## 📞 Support

If you encounter any issues:
1. Check the Troubleshooting section above
2. Review application logs in the console
3. Verify all prerequisites are met
4. Ensure all configuration is correct

---

**Happy Coding! 🚀**

---

*Last Updated: February 2024*
*Version: 1.0.0*
