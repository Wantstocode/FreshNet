package com.kiran.major.service;

import com.kiran.major.model.Product;
import com.kiran.major.model.Roles;
import com.kiran.major.model.User;
import com.kiran.major.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RolesService rolesService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public UserRepository userRepository;

    @Transactional
    @Override
    public User save(User user, String url) {
        String password=user.getPassword();
        if(password!=null)
            user.setPassword(bCryptPasswordEncoder.encode(password));


//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);

        List<Roles> roles=new ArrayList<>();
        roles.add(rolesService.findById(2));
        System.out.println("user");
        user.setRoles(roles);
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User newUser=userRepository.save(user);

        if(newUser!=null){
            sendMailForAccountVerification(user,url);
        }

        return newUser;
    }

    @Transactional
    @Override
    public User saveAsAdmin(User user, String url) {
        String password=user.getPassword();
        if(password!=null)
            user.setPassword(bCryptPasswordEncoder.encode(password));


//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);

        List<Roles> roles=new ArrayList<>();
        roles.add(rolesService.findById(1));
        System.out.println("admin");
        user.setRoles(roles);
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User newUser=userRepository.save(user);

        if(newUser!=null){
            sendMailForAccountVerification(user,url);
        }

        return newUser;
    }

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Override
    public void sendMailForAccountVerification(User user, String path) {

        String from = senderEmail;
        String to = user.getEmail();
        String subject="Account Verification";
        String content= "Dear [[firstname]] [[lastname]],<br>" + "Please click the link below to verify your account"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a><h3>"+"Thank You";

        try{
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message);

            helper.setFrom(from,"FreshNet");
            helper.setTo(to);
            helper.setSubject(subject);

            content=content.replace("[[firstname]]",user.getFirstName());
            content=content.replace("[[lastname]]",user.getLastName());

            String siteUrl=path + "/verify?code=" + user.getVerificationCode();      http://localhost:8080/verify?code=48296196-3f36-4a80-ac6c-01490d7073da
            System.out.println(siteUrl);

            content=content.replace("[[URL]]",siteUrl);

            helper.setText(content,true);

            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean verifyAccount(String verificationCode) {
        User user=userRepository.findUserByVerificationCode(verificationCode);
        if(user==null){
            return false;
        }
        user.setEnable(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public void sendMailForForgotPassword(User user,String email, int otp) {

        String from = senderEmail;
        String to = email;
        String subject="Forgot password";
        String content= "Dear [[firstname]] [[lastname]],<br>" + "this is the otp for your password change"
                + "<h1> OTP :"+otp;

        try{
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message);

            helper.setFrom(from,"FreshNet");
            helper.setTo(to);
            helper.setSubject(subject);

            content=content.replace("[[firstname]]",user.getFirstName());
            content=content.replace("[[lastname]]",user.getLastName());

            helper.setText(content,true);

            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public User findById(int id) {
        Optional<User> result = userRepository.findById(id);
        User user;

        if(result!=null) {
            user = result.get();
            return user;
        }
        else
            return null;
    }

}
