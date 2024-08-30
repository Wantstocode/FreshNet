package com.kiran.major.controller;

import com.kiran.major.model.User;
import com.kiran.major.repository.UserRepository;
import com.kiran.major.service.RolesService;
import com.kiran.major.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class LoginControll {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpSession session;

    Random random=new Random(1000);

    @Autowired
    UserService userService;

    @Autowired
    RolesService rolesService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


   @GetMapping("/login")
   public String loginForm(){
       return "login";
   }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request,Model model)throws ServletException {

        if (bindingResult.hasErrors()) {
            // If there are errors, return to the registration page with the errors displayed
            return "register";
        }

        String url=request.getRequestURL().toString();                      http://localhost:8080/register
        url=url.replace(request.getServletPath(),"");             http://localhost:8080
        System.out.println(url);
        String password= user.getPassword();
        userService.save(user,url);
        model.addAttribute("emailSentMessage","Verification link sent to your email " + user.getEmail() + ".<br/> click that to verify the account.");

        return "register";
    }


    @GetMapping("/admin/register")
    public String showAdminRegistrationForm(Model model){
        model.addAttribute("user",new User());
        return "admin_register";
    }

    @PostMapping("/admin/register")
    public String registerAdminAccount(@Valid @ModelAttribute("user") User user,BindingResult bindingResult, HttpServletRequest request,Model model)throws ServletException {

        if (bindingResult.hasErrors()) {
            // If there are errors, return to the registration page with the errors displayed
            return "admin_register";
        }

        String url=request.getRequestURL().toString();                      http://localhost:8080/admin/register
        System.out.println(url);
        url=url.replace(request.getServletPath(),"");             http://localhost:8080
        System.out.println(url);
        String password= user.getPassword();
        userService.saveAsAdmin(user,url);
        model.addAttribute("emailSentMessage","Verification link sent to your email " + user.getEmail() + ".<br/> click that to verify the account.");
//        request.login(user.getEmail(),password);

        return "admin_register";
    }


    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code,Model model){

       boolean v=userService.verifyAccount(code);
       if(v){
           model.addAttribute("msg","Your account is successfully verified");
       }else{
           model.addAttribute("msg","may be your account not registered successfully or your account is already verified");
       }
       return "message";
    }

    @GetMapping("/forgotpassword")
    public String openEmailForm(){

       return "forgot_email_form.html";
    }

    @PostMapping("/sendOTP")
    public String sendOTP(@RequestParam("email") String email, Model model){

        try {
            User user=userRepository.findUserByEmail(email);
            if(user==null){
                throw new RuntimeException("User not found");
            }
            System.out.println(email);
            int otp=random.nextInt(99999);

            session.setAttribute("myOTP",otp);
            session.setAttribute("email",email);

            System.out.println(otp);
            userService.sendMailForForgotPassword(user,email,otp);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage() + ", with Email Id you provided";
            model.addAttribute("errorMessage", errorMessage);
            return "forgot_email_form.html"; // Return to the registration page with error message
        }
        return "verify_otp";
    }

    @PostMapping("/verifyotp")
    public String verifyOTP(@RequestParam("otp")int otp,Model model){
       int myOtp=(int)session.getAttribute("myOTP");
       if(myOtp==otp){
           return "password_change_form";
       }
       else{
           model.addAttribute("errorMessage","You have entered wrong OTP !!<br/> check it once");
           return "verify_otp";
       }
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword")String newPassword,
                                 @RequestParam("cnewpassword")String confirmNewPassword,
                                 Model model){
       if(!newPassword.equals(confirmNewPassword)){
           model.addAttribute("errorMessage","new password and confirm password should be same...");
           return "password_change_form";
       }
       String email=(String)session.getAttribute("email");
       User user=userRepository.findUserByEmail(email);
       user.setPassword(bCryptPasswordEncoder.encode(newPassword));
       userRepository.save(user);
       model.addAttribute("successMessage","Your password successfully changed");

       return "login";

    }


    @GetMapping("/accessdenied")
    public String accessdeniedPage(){
       return "accessdenied";
    }

    @GetMapping("/default")
    public String pageAfterLogin(HttpServletRequest request, Principal principal,Model model){
       if(request.isUserInRole("ADMIN")) {

           return "redirect:/admin";
       }
       return "redirect:/home";
    }



}

