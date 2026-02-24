# Passport Automation System - Quick Start Guide

## 🚀 Quick Setup (5 Minutes)

### Step 1: Prerequisites Check
Ensure you have:
- ✅ Java 21 installed
- ✅ MySQL 8.0+ installed and running
- ✅ Maven installed
- ✅ IDE (VS Code/IntelliJ/Eclipse)

### Step 2: Database Setup
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE passport_db;
exit;
```

### Step 3: Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4: Run the Application
```bash
# Navigate to project directory
cd passport-automation-system

# Build and run
mvn spring-boot:run
```

### Step 5: Access the Application
Open browser: http://localhost:8080

## 📝 Test the Application

### Create Test Users

#### Option 1: Register through UI
1. Go to http://localhost:8080/register
2. Fill the form and submit

#### Option 2: Insert directly in MySQL
```sql
USE passport_db;

-- Applicant User
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Test Applicant', '1990-01-01', 'Test Address', 'applicant@test.com', '9876543210', 'test123', 'APPLICANT');

-- Officer User
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Test Officer', '1985-01-01', 'Officer Address', 'officer@test.com', '9876543211', 'test123', 'PASSPORT_OFFICER');

-- Police User
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Test Police', '1987-01-01', 'Police Address', 'police@test.com', '9876543212', 'test123', 'POLICE_VERIFICATION_OFFICER');

-- Admin User
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES ('Test Admin', '1980-01-01', 'Admin Address', 'admin@test.com', '9876543213', 'test123', 'ADMINISTRATOR');
```

### Test Login Credentials

| Role | Email | Password |
|------|-------|----------|
| Applicant | applicant@test.com | test123 |
| Officer | officer@test.com | test123 |
| Police | police@test.com | test123 |
| Admin | admin@test.com | test123 |

## 🔍 Testing Workflow

### As Applicant:
1. Login with applicant@test.com
2. Click "Apply for New Passport"
3. Fill application form
4. Upload documents (any PDF/image files for testing)
5. Submit application
6. Book appointment (optional)
7. Track application status

### As Police Officer:
1. Login with police@test.com
2. View pending verifications
3. Select an application
4. Mark as VERIFIED or NOT_VERIFIED

### As Passport Officer:
1. Login with officer@test.com
2. View applications under verification
3. Review application details
4. Approve or reject application

### As Administrator:
1. Login with admin@test.com
2. View dashboard statistics
3. Manage all users
4. View all applications

## 🛠️ Troubleshooting

### Problem: Database connection failed
**Solution**: 
- Check MySQL is running: `sudo systemctl status mysql`
- Verify credentials in application.properties
- Ensure database exists: `SHOW DATABASES;`

### Problem: Port 8080 already in use
**Solution**:
Add to application.properties:
```properties
server.port=8081
```

### Problem: File upload error
**Solution**:
- Create uploads directory: `mkdir uploads`
- Check file size (max 10MB)

### Problem: Application won't start
**Solution**:
```bash
# Clean and rebuild
mvn clean install

# Check Java version
java --version  # Should be 21+
```

## 📁 Project Structure Quick Reference

```
passport-automation-system/
├── src/main/java/com/passport/automation/
│   ├── controller/          # HTTP request handlers
│   ├── domain/             # Data models (User, Application)
│   ├── repository/         # Database operations
│   └── service/            # Business logic
├── src/main/resources/
│   ├── templates/          # HTML pages
│   └── application.properties
├── database-schema.sql     # Database setup script
├── pom.xml                # Maven dependencies
└── README.md              # Full documentation
```

## 🎯 Key URLs

| Page | URL |
|------|-----|
| Home | http://localhost:8080/ |
| Login | http://localhost:8080/login |
| Register | http://localhost:8080/register |
| Applicant Dashboard | http://localhost:8080/applicant/dashboard |
| Officer Dashboard | http://localhost:8080/officer/dashboard |
| Police Dashboard | http://localhost:8080/police/dashboard |
| Admin Dashboard | http://localhost:8080/admin/dashboard |

## 📊 Application Status Flow

```
SUBMITTED 
    ↓
UNDER_VERIFICATION (after police verification)
    ↓
APPROVED or REJECTED (by passport officer)
```

## 🔐 Security Note

⚠️ This is a demo application. For production:
- Implement password encryption (BCrypt)
- Add Spring Security
- Use HTTPS
- Implement CSRF protection
- Add input validation
- Implement rate limiting

## 📚 Next Steps

1. ✅ Complete basic testing
2. ✅ Add more test users
3. ✅ Submit test applications
4. ✅ Test complete workflow
5. 📖 Read full documentation in README.md
6. 🎨 Customize templates as needed
7. 🔧 Add additional features

## 💡 Tips

- Use **Postman** to test API endpoints
- Enable **Spring Boot DevTools** for auto-reload
- Check **logs** in console for debugging
- Use **MySQL Workbench** for database management

## 📞 Support

For detailed documentation, see:
- README.md - Complete setup guide
- PROJECT-STRUCTURE.md - Architecture details
- database-schema.sql - Database reference

---

**Happy Coding! 🎉**

Student: KEERTHIVASAN S (311123205031)  
Course: CCS342 - DevOps Lab  
Institution: LICET, Chennai
