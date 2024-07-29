package com.kiran.major.service;

import com.kiran.major.dto.OrderRequest;
import com.kiran.major.model.Order;
import com.kiran.major.model.User;

import java.util.List;

public interface OrderService {
    public void saveOrder(OrderRequest orderRequest, Integer userId);

    public List<Order> findOrderByUser(User user);

    public boolean cancelOrder(int id);

    List<Order> findAllOrders();

    boolean updateOrderStatus(int id,String status);

    Order findOrderByOrderReferenceId(String id);
}
