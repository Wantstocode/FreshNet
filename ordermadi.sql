create database if not exists e_commerce;
use e_commerce;

drop table if exists category;
create table category(
id int(10) primary key auto_increment,
name varchar(50)
);

drop table if exists product;
CREATE TABLE product (
    product_id INT(10) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    category_id INT,
    price DOUBLE,
    weight DOUBLE,
    description VARCHAR(100),  -- Corrected typo
    imagename VARCHAR(250),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

drop table if exists roles;
create table roles(
role_id int(10) primary key auto_increment,
role_name varchar(50) 
);

insert into roles value
(1,"ROLE_ADMIN"),
(2,"ROLE_USER");


drop table if exists user;
create table user(
user_id int(15) primary key auto_increment,
first_name varchar(50),
last_name varchar(50),
email varchar(50) unique,
password varchar(68) 
);

drop table if exists user_roles;
create table user_roles(
user_id int(15),
role_id int(15),
primary key(user_id,role_id),
foreign key(user_id) references user(user_id),
foreign key(role_id) references roles(role_id)
);




