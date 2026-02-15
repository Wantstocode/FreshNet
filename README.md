# 🛒 FreshNet – Online Grocery Ordering Web Application

**FreshNet** is a full-stack online grocery ordering platform built using **Spring Boot (backend)** and **Thymeleaf (frontend)**.
It provides a smooth shopping experience for users while giving administrators powerful tools for product and inventory management.

The project demonstrates real-world backend engineering concepts including authentication, security, database relationships, and production-style application architecture.

---

## 🚀 Tech Stack

**Backend**

* Java + Spring Boot
* Spring Security
* OAuth2 (Google Sign-In)
* MySQL Database

**Frontend**

* Thymeleaf
* HTML, CSS, JavaScript

**Other Integrations**

* Email Service (verification & password reset)
* Role-based authorization
* Secure authentication workflows

---

## ⭐ Key Features

### 👤 User & Admin Roles

* **Users:** Browse products, add to cart, place orders.
* **Admins:** Manage products, users, and inventory.

---

### 🔐 Authentication & Security

* Google OAuth2 Sign-In integration
* Email verification after registration
* Password reset via email
* Secure login with role-based access control

---

### 🛍️ Product Management

* Product search with keywords
* Sorting by price or name
* Pagination for better browsing
* Add-to-cart functionality
* Order placement workflow

---

### 🗄️ Database Design

Uses **MySQL relational database** with structured relationships:

* One-to-One
* One-to-Many
* Many-to-Many

Designed to reflect real production database architecture.

---

## 📧 Email Features

✔ Email verification on signup
✔ Prevents login with unverified emails
✔ Forgot password email workflow

Ensures secure and verified user access.

---

## ⚙️ Setup Instructions

### 1️⃣ Database Setup

* Run the provided SQL script.
* Default users receive **USER role**.
* Admin roles must be assigned manually in DB.

---

### 2️⃣ Google OAuth Configuration

1. Create credentials in Google Cloud Console:

   * Client ID
   * Client Secret
2. Add them to:

`application.properties`

(Note: Credentials excluded from repo for security.)

---

## 🔑 Login Access

### User Login

* Email + password
* Access shopping functionality

### Admin Login

* Elevated privileges
* Manage products and users

---

## 💡 Learning Highlights From This Project

* Spring Boot full-stack development
* Secure authentication implementation
* OAuth2 integration
* Role-based authorization
* Database relationship modeling
* Production-style backend architecture

---

## 👨‍💻 Author

Developed as a backend-focused full-stack project demonstrating real-world engineering practices using Java Spring Boot.

If you found this project helpful, feel free to ⭐ the repository.


## ER-Diagram
<br>
<br>

![ordermadi-ER](https://github.com/user-attachments/assets/3b36453f-7e2e-4d8a-ac06-d57131f4ed32)
<br>
<br>
**login page**
<br>
<br>
<img width="1905" height="955" alt="Screenshot 2026-02-15 141016" src="https://github.com/user-attachments/assets/46c9400a-aad8-4d45-8296-c9415ada2730" />
<br>
<br>
**Registration Page**
<br>
<br>
![Screenshot (65)](https://github.com/user-attachments/assets/2b8d43b5-6556-4be5-af48-f58f0572e4a8)
<br>
<br>
**Admin Page**
<br>
<br>
![Screenshot (66)](https://github.com/user-attachments/assets/48ab2c89-868f-4d73-9ed2-cea0bfd1ece2)
<br>
<br>
**Shopping Page**
<br>
<br>
![Screenshot (67)](https://github.com/user-attachments/assets/4468ba8a-6b24-4541-940c-af5153999e1b)
<br>
<br>
**Cart Product Checkout**
<br>
<br>
![Screenshot (68)](https://github.com/user-attachments/assets/1c3ec589-8406-4b18-8196-c3c9884621d6)
<br>
<br>
**My Orders**
![image](https://github.com/user-attachments/assets/0d258ecd-f391-4793-b7e4-6b4d451ea1e1)






