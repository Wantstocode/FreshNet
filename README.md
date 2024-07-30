## FreahNet
FreashNet is an online grocery ordering web application built using Spring Boot for the backend and Thymeleaf for the frontend. It offers a seamless experience for users to browse, select, and purchase groceries. The application also features inventory management, role-based authorization, Google sign-in integration, and robust security measures.
<br>
## Key Features

1. Spring Boot Backend: Provides a robust and scalable backend.
2. Thymeleaf Frontend: Renders dynamic content and views.
3. User and Admin Roles:
      * Users: Access only the shopping site.
      * Admins: Have access to both user and admin functionalities.
4. Google Sign-In Integration: Allows users to authenticate using their Google accounts.
5. Security Measures: Implements strong security protocols to protect user data.
6. Data Storage: Utilizes MySQL as a relational database with five tables having various relationships:
      * OneToOne
      * OneToMany
      * ManyToMany
7. Email Verification: Sends a verification email to users upon registration to confirm their email address. Ensuring that Only verified emails can log in. Invalid or false email addresses will not be able to access the application.
8. Forgot Password: Allows users to reset their passwords via email if they forget their credentials.
9. Product Management:
     * Sorting: Allows sorting products by price and name to help users find items more easily.
     * Searching: Enables users to search for products using keywords.
     * Pagination: Implements pagination to manage large sets of products, improving the browsing experience.
     * Selection and Cart Management: Users can select products, add them to their cart, and proceed to place orders.
       
## Login credentials

1. User Login: Users can log in using their email and password to access the shopping site
2. Admin Login: Admins can log in with additional privileges to manage both user and admin functionalities.

## Setup Instructions

1.Database Initialization:

* Execute the provided SQL query to set up the initial database schema and data.
* By default, users are assigned the 'User' role. Admins need to be manually added to the database, paying attention to ManyToMany mapping.

2. Google Authentication:
   * Create your own google-client-id and google-client-secret credentials.
   * Configure these credentials in the application.properties file. (Note: Sensitive information has been omitted for privacy.)
   * This may help for google oauth configure: https://www.youtube.com/watch?v=qcz2jBLNOtc

## ER-Diagram

![ordermadi-ER](https://github.com/user-attachments/assets/3b36453f-7e2e-4d8a-ac06-d57131f4ed32)
<br>
<br>
**login page**
<br>
<br>
![Screenshot (64)](https://github.com/user-attachments/assets/d09a7751-f91e-49ac-b364-75fd8a4a3472)
<br>
<br>
**Registration Page**
<br>
<br>
![Screenshot (65)](https://github.com/user-attachments/assets/2b8d43b5-6556-4be5-af48-f58f0572e4a8)
<br>
<br>
**login page**
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






