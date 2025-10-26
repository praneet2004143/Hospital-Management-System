CREATE DATABASE hospital_db;
USE hospital_db;

CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    contact VARCHAR(15),
    address VARCHAR(100),
    disease VARCHAR(50)
);
