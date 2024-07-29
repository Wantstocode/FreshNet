package com.kiran.major.repository;

import com.kiran.major.model.Order;
import com.kiran.major.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    public List<Order> findOrderByUser(User user);

    public Order findOrderByOrderReferenceId(String id);
}
