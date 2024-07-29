package com.kiran.major.configuration;

import com.kiran.major.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Lazy
    @Autowired
    GoogleOauth2SuccessHandler googleOauth2SuccessHandler;

    @Autowired
    public CustomUserDetailService customUserDetailService;

    @Bean
     public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(Configurer -> Configurer
                        .requestMatchers("/", "/shop/**", "/register", "/admin/register","/verify","/forgotpassword","/sendOTP","/verifyotp","/change-password")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                //oauth login configuration
                        .oauth2Login(oauth->oauth
                                .loginPage("/login")
                                .successHandler(googleOauth2SuccessHandler)
                        )

                //form login
                .formLogin(form -> form

                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/default",true)
                        .usernameParameter("email")
                        .passwordParameter("password")

                )
                .logout(logout -> logout.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")

                )
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/accessdenied")
                );
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}
