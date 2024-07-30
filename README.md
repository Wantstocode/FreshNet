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
## Login Page

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

<br>
<br>
![Screenshot (55)](https://github.com/Wantstocode/FreahNet/assets/120893466/7136656f-079f-4649-852a-1d00bc0c79a4)
<br>
<br>
**Registration Page**
<br>
<br>
![Screenshot (61)](https://github.com/Wantstocode/FreahNet/assets/120893466/4e1631c7-dd28-4e19-ad51-5822254a41a7)
<br>
<br>
**Admin Home Page**
<br>
<br>
![Screenshot (56)](https://github.com/Wantstocode/FreahNet/assets/120893466/c68cb8f1-9bd2-451b-a09b-f43a2089f4c8)
<br>
<br>
**User Home Page**
<br>
<br>
![Screenshot (57)](https://github.com/Wantstocode/FreahNet/assets/120893466/0b8528d0-bf95-4e57-b080-323666f9aad5)
<br>
<br>
**User Shopping Page**
<br>
<br>
![Screenshot (58)](https://github.com/Wantstocode/FreahNet/assets/120893466/04f43c3b-7328-499f-8589-4efbce2aabff)



