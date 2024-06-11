package com.sheryians.major.service;

import com.sheryians.major.model.Roles;
import com.sheryians.major.model.User;
import com.sheryians.major.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RolesService rolesService;

    @Autowired
    public UserRepository userRepository;

    @Transactional
    @Override
    public User save(User user) {
        String password=user.getPassword();
        if(password!=null)
            user.setPassword(bCryptPasswordEncoder.encode(password));


//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);

        List<Roles> roles=new ArrayList<>();
        roles.add(rolesService.findById(2));
        user.setRoles(roles);
       return userRepository.save(user);
    }

}
