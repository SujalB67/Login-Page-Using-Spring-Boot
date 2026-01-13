-- Create database
CREATE DATABASE IF NOT EXISTS login_db;
USE login_db;

-- Create users table (same as your Tomcat project)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample users (same as your Tomcat project)
INSERT INTO users (username, password, name, email) VALUES
('admin', 'admin123', 'System Administrator', 'admin@example.com'),
('user', 'user123', 'Regular User', 'user@example.com'),
('john', 'john123', 'John Doe', 'john@example.com'),
('test', 'test123', 'Test User', 'test@example.com');

-- Display inserted users
SELECT * FROM users;
