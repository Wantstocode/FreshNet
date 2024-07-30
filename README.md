# FreahNet
This is a online grocery ordering web application built using Spring Boot as Backend and thymeleaf as frountend. This application offers users a seamless experience to browse, select, and purchase groceries, inventory management, role-based authorization, Google sign-in integration, and robust security measures.
<br>
I have used Spring MVC as a basic work flow of a website, where a request is recived from client and the responds is sent from the server as a view with dynamic content with the help of API. 
<br>
For data storage MySQL as been used, which is a relational database. There are basically five tables with relationship between each other(OneToOne, OnetoMany, ManyToMany) 
<br>
<br>
**Login Page**
<br>
This is logic page, where user and admin can login using their email and password, user will be having access to only shopping user site, whereas admin have access to both.
before running the application, execute the query given. while registaring the user by default role will be set as 'User' and admin as to be entered in database(keep eyes on ManytoMany mapping while entering the admin).
<br>
For the Google Authentication u have to create ur own 'google-client-id' and 'google-client-secreate' and configur in Application.properties file. which i have removed because of privary perpose.
<br>
This may help for google oauth configure: https://www.youtube.com/watch?v=qcz2jBLNOtc
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



