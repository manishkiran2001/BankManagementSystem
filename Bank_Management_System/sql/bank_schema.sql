-- ========================
-- DATABASE: bank_management_system
-- ========================

-- TABLE: AdminDetails
CREATE TABLE admindetails (
  AdminId INT PRIMARY KEY AUTO_INCREMENT,
  AdminEmail VARCHAR(45) NOT NULL UNIQUE,
  AdminPassword VARCHAR(4) NOT NULL
);

INSERT INTO admindetails (AdminId, AdminEmail, AdminPassword)
VALUES (1, 'manish@gmail.com', '8464');

-- ------------------------

-- TABLE: CustomerDetails
CREATE TABLE customer_details (
  Customer_Id INT PRIMARY KEY AUTO_INCREMENT,
  Customer_Name VARCHAR(20) NOT NULL,
  Customer_Email VARCHAR(45) NOT NULL UNIQUE,
  Customer_MobileNum BIGINT NOT NULL UNIQUE,
  Customer_Aadhar_Number BIGINT NOT NULL UNIQUE,
  Customer_Address VARCHAR(45) NOT NULL,
  Customer_Gender VARCHAR(10) NOT NULL,
  Customer_Account_Number BIGINT UNIQUE,
  Customer_Pin INT,
  Customer_Amount DOUBLE NOT NULL,
  Customer_Status VARCHAR(20)
);

-- Sample Insert (You can trim or keep all based on need)
INSERT INTO customer_details (
  Customer_Id, Customer_Name, Customer_Email, Customer_MobileNum, Customer_Aadhar_Number,
  Customer_Address, Customer_Gender, Customer_Account_Number, Customer_Pin,
  Customer_Amount, Customer_Status
) VALUES 
(1, 'manish', 'ankahd20@gmail.com', 9292992929, 282828282828, '', 'male', 456643024091, 4321, 4576, 'closed'),
(2, 'manish', 'manish@gmail.com', 8464992957, 805127518809, '', 'male', 251379327412, 1234, 6800, 'closed');

-- ------------------------

-- TABLE: CustomerStatement
CREATE TABLE customer_statement (
  Transaction_Id INT PRIMARY KEY AUTO_INCREMENT,
  Transaction_Type VARCHAR(45) NOT NULL,
  Transaction_Amount INT NOT NULL,
  Balance_Amount INT NOT NULL,
  Customer_AccNum BIGINT NOT NULL,
  Transaction_Date_And_Time VARCHAR(45) NOT NULL
);

-- Sample Transactions
INSERT INTO customer_statement (
  Transaction_Id, Transaction_Type, Transaction_Amount, Balance_Amount,
  Customer_AccNum, Transaction_Date_And_Time
) VALUES 
(1, 'debit', 200, 5000, 283829292292, '28-12-2025 12:30pm'),
(2, 'credit', 2500, 500, 482930207524, '28 Jul, 2025 3:52:34 PM');
