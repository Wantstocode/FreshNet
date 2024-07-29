package com.sheryians.major.controller;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.model.Roles;
import com.sheryians.major.model.User;
import com.sheryians.major.repository.RolesRepository;
import com.sheryians.major.repository.UserRepository;
import com.sheryians.major.service.RolesService;
import com.sheryians.major.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginControll {

    @Autowired
    UserService userService;

    @Autowired
    RolesService rolesService;



   @GetMapping("/login")
   public String loginForm(){
       GlobalData.cart.clear();
       return "login";
   }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") User user, HttpServletRequest request)throws ServletException {

        String password= user.getPassword();
        userService.save(user);
        request.login(user.getEmail(),password);

        return "redirect:/home";
    }

    @GetMapping("/accessdenied")
    public String accessdeniedPage(){
       return "accessdenied";
    }

    @GetMapping("/default")
    public String pageAfterLogin(HttpServletRequest request){
       if(request.isUserInRole("ADMIN"))
           return "redirect:/admin";
       return "redirect:/home";
    }



}

