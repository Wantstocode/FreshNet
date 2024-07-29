package com.kiran.major.service;


import com.kiran.major.model.Product;
import com.kiran.major.model.User;


public interface UserService {

    User save(User user,String url);

    public User saveAsAdmin(User user, String url);

    void sendMailForAccountVerification(User user,String path);

    boolean verifyAccount(String verificationCode);

    void sendMailForForgotPassword(User user,String email,int otp);

    User findById(int id);



}
