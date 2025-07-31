# 🏦 Bank Management System (Java Console Application)

This is a Java-based **Bank Management System** project built using core Java, JDBC, and MySQL. It simulates basic banking operations like customer registration, login, deposits, withdrawals, account management, and admin functionalities — all through a console-based interface.

---

## 📌 Features

### 👤 Customer Features
- Customer Registration (with input validation using Regex)
- Login using Email/Account Number + PIN + Captcha
- Deposit Amount
- Withdraw Amount
- Check Account Balance
- View Transaction Statement
- Update PIN
- Close Bank Account

### 🛠️ Admin Features
- Admin Login
- View All Customers
- Approve Customer Account Creation Requests
- Approve Account Closures

---

## 🧰 Technologies Used

| Technology | Description                |
|------------|----------------------------|
| Java       | Core application logic     |
| JDBC       | Database connectivity      |
| MySQL      | Database backend           |
| Eclipse    | Development IDE            |

---

## 🗃️ Database Structure (MySQL)

- `CustomerDetails`  
- `AccountDetails`  
- `CustomerStatement`  
- `AdminDetails`

> Ensure you have created the required tables with correct column names and data types.  
> Also, fix typos like `Transaction_Ammount` → `Transaction_Amount`.

---

## ✅ Validation Logic

- **Name**: Only alphabets and spaces
- **Email**: Standard email format (e.g., user@example.com)
- **Mobile**: 10-digit numbers only
- **Aadhar**: 12-digit numeric only
- **Gender**: Only `Male`, `Female`, or `Others` (case-insensitive)
- **PIN**: Exactly 4 digits

---

## 🏁 How to Run

1. Clone this repository or download the ZIP.
2. Import it into Eclipse as a Java Project.
3. Ensure MySQL DB is running and tables are created.
4. Update your DB connection credentials in the code.
5. Run `BankMainClass.java` and follow console instructions.

---

## 📦 Project Structure

BankManagementSystem/
│
├── com.bank.main/ # Main entry class
├── com.bank.model/ # Java Bean classes
├── com.bank.DAO/ # Data Access Objects (JDBC code)
├── com.bank.service/ # Business logic
├── com.bank.validation/ # Regex validations
├── com.bank.exception/ # Custom exception handling
└── README.md # Project documentation


---

## 🙋‍♂️ Author

**Manish Kiran**  
Email: manishkiran2001@gmail.in  

---

## 📃 License

This project is for educational purposes only. You may modify and use it for learning, practicing Java, or demonstrating projects in interviews.

