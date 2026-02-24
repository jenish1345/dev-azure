-- Passport Automation System Database Schema
-- Database: passport_db

-- Create Database
CREATE DATABASE IF NOT EXISTS passport_db;
USE passport_db;

-- Table: users
-- Stores all system users (Applicants, Officers, Police, Admin)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    address TEXT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_number VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('APPLICANT', 'PASSPORT_OFFICER', 'POLICE_VERIFICATION_OFFICER', 'ADMINISTRATOR') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: passport_applications
-- Stores passport application details
CREATE TABLE IF NOT EXISTS passport_applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    place_of_birth VARCHAR(100) NOT NULL,
    current_address TEXT NOT NULL,
    permanent_address TEXT NOT NULL,
    application_type ENUM('NEW_PASSPORT', 'RENEWAL', 'REISSUE', 'TATKAL') NOT NULL,
    identity_proof_path VARCHAR(255),
    address_proof_path VARCHAR(255),
    status ENUM('SUBMITTED', 'UNDER_VERIFICATION', 'APPROVED', 'REJECTED') DEFAULT 'SUBMITTED',
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    appointment_date DATE,
    appointment_time VARCHAR(20),
    police_verification_status ENUM('PENDING', 'VERIFIED', 'NOT_VERIFIED') DEFAULT 'PENDING',
    rejection_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_police_verification (police_verification_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert Sample Users for Testing

-- Applicant
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES 
('John Doe', '1990-01-15', '123 Main Street, Chennai, Tamil Nadu', 'applicant@test.com', '9876543210', 'password123', 'APPLICANT'),
('Jane Smith', '1992-05-20', '456 Park Avenue, Mumbai, Maharashtra', 'jane@test.com', '9876543214', 'password123', 'APPLICANT');

-- Passport Officer
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES 
('Officer Rajesh Kumar', '1985-03-10', '789 Government Office, Chennai', 'officer@test.com', '9876543211', 'officer123', 'PASSPORT_OFFICER'),
('Officer Priya Sharma', '1988-07-25', '321 Passport Office, Delhi', 'priya.officer@test.com', '9876543215', 'officer123', 'PASSPORT_OFFICER');

-- Police Verification Officer
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES 
('Inspector Suresh Kumar', '1987-08-12', '555 Police Station, Chennai', 'police@test.com', '9876543212', 'police123', 'POLICE_VERIFICATION_OFFICER'),
('Inspector Anita Desai', '1989-11-30', '777 Police HQ, Bangalore', 'anita.police@test.com', '9876543216', 'police123', 'POLICE_VERIFICATION_OFFICER');

-- Administrator
INSERT INTO users (name, date_of_birth, address, email, mobile_number, password, role) 
VALUES 
('Admin User', '1980-12-05', '999 Admin Office, New Delhi', 'admin@test.com', '9876543213', 'admin123', 'ADMINISTRATOR');

-- Insert Sample Applications for Testing

INSERT INTO passport_applications 
(user_id, full_name, date_of_birth, place_of_birth, current_address, permanent_address, 
 application_type, identity_proof_path, address_proof_path, status, police_verification_status) 
VALUES 
(1, 'John Doe', '1990-01-15', 'Chennai', '123 Main Street, Chennai', '123 Main Street, Chennai', 
 'NEW_PASSPORT', 'uploads/id_proof_1.pdf', 'uploads/address_proof_1.pdf', 'SUBMITTED', 'PENDING'),
(2, 'Jane Smith', '1992-05-20', 'Mumbai', '456 Park Avenue, Mumbai', '456 Park Avenue, Mumbai', 
 'RENEWAL', 'uploads/id_proof_2.pdf', 'uploads/address_proof_2.pdf', 'UNDER_VERIFICATION', 'VERIFIED');

-- Views for Statistics (Optional)

-- View: Application Statistics by Status
CREATE OR REPLACE VIEW application_statistics AS
SELECT 
    status,
    COUNT(*) AS count
FROM passport_applications
GROUP BY status;

-- View: Applications Pending Police Verification
CREATE OR REPLACE VIEW pending_police_verification AS
SELECT 
    pa.application_id,
    pa.full_name,
    u.email,
    u.mobile_number,
    pa.submission_date,
    pa.police_verification_status
FROM passport_applications pa
JOIN users u ON pa.user_id = u.user_id
WHERE pa.police_verification_status = 'PENDING';

-- View: Recent Applications (Last 30 days)
CREATE OR REPLACE VIEW recent_applications AS
SELECT 
    pa.application_id,
    pa.full_name,
    pa.application_type,
    pa.status,
    pa.submission_date,
    u.email,
    u.mobile_number
FROM passport_applications pa
JOIN users u ON pa.user_id = u.user_id
WHERE pa.submission_date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)
ORDER BY pa.submission_date DESC;

-- Stored Procedures (Optional)

DELIMITER //

-- Procedure: Get Application Count by Status
CREATE PROCEDURE GetApplicationCountByStatus(IN app_status VARCHAR(50))
BEGIN
    SELECT COUNT(*) AS application_count
    FROM passport_applications
    WHERE status = app_status;
END //

-- Procedure: Get User Applications
CREATE PROCEDURE GetUserApplications(IN p_user_id INT)
BEGIN
    SELECT 
        application_id,
        full_name,
        application_type,
        status,
        submission_date,
        appointment_date,
        police_verification_status
    FROM passport_applications
    WHERE user_id = p_user_id
    ORDER BY submission_date DESC;
END //

-- Procedure: Update Application Status
CREATE PROCEDURE UpdateApplicationStatus(
    IN p_application_id INT,
    IN p_status VARCHAR(50),
    IN p_rejection_reason TEXT
)
BEGIN
    UPDATE passport_applications
    SET 
        status = p_status,
        rejection_reason = p_rejection_reason,
        updated_at = CURRENT_TIMESTAMP
    WHERE application_id = p_application_id;
END //

DELIMITER ;

-- Indexes for Performance Optimization
CREATE INDEX idx_submission_date ON passport_applications(submission_date);
CREATE INDEX idx_appointment_date ON passport_applications(appointment_date);

-- Comments on Tables
ALTER TABLE users COMMENT = 'Stores all system users including applicants, officers, police officers, and administrators';
ALTER TABLE passport_applications COMMENT = 'Stores passport application details with status tracking';

COMMIT;
