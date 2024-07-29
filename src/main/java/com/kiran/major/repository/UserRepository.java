package com.kiran.major.repository;


import com.kiran.major.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
   public User findUserByEmail(String email);

   public User findUserByVerificationCode(String code);
}
