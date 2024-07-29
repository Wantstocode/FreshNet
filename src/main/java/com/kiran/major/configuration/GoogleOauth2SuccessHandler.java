package com.kiran.major.configuration;

import com.kiran.major.model.Roles;
import com.kiran.major.model.User;
import com.kiran.major.repository.UserRepository;
import com.kiran.major.service.RolesService;
import com.kiran.major.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class GoogleOauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesService rolesService;

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

   @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token=(OAuth2AuthenticationToken) authentication;
        String email=token.getPrincipal().getAttribute("email").toString();
        if(userRepository.findUserByEmail(email)==null){
            User user=new User();
            user.setFirstName(token.getPrincipal().getAttribute("given_name").toString());
            user.setLastName(token.getPrincipal().getAttribute("family_name").toString());
            user.setEmail(email);
            List<Roles> roles=new ArrayList<>();
            roles.add(rolesService.findById(2));
            user.setRoles(roles);
            user.setEnable(true);
            userRepository.save(user);
        }

//        DefaultOAuth2User user=(DefaultOAuth2User) authentication.getPrincipal();
//
//        User user1=new User();
//        user1.setFirstName(user.getAttribute("given_name").toString());
//        user1.setLastName(user.getAttribute("family_name").toString());
//        user1.setEmail(user.getAttribute("email").toString());
//        List<Roles> roles=new ArrayList<>();
//        roles.add(rolesService.findById(2));
//        user1.setRoles(roles);

//        System.out.println(user1);
//        if(userRepository.findUserByEmail(user.getAttribute("email").toString())==null){
//            userService.saveUserThroughGoogleAccount(user1);
//        }

        redirectStrategy.sendRedirect(request,response,"/");
    }
}
