package com.kiran.major.service;

import com.kiran.major.dto.OrderRequest;
import com.kiran.major.model.Order;
import com.kiran.major.model.User;
import com.razorpay.RazorpayException;

import java.util.List;

public interface OrderService {
    public Order saveOnlinePaymentOrder(OrderRequest orderRequest, Integer userId) throws RazorpayException;

    public void saveOfflinePaymentOrder(OrderRequest orderRequest,Integer userId);

    public List<Order> findOrderByUser(User user);

    public boolean cancelOrder(int id);

    List<Order> findAllOrders();

    boolean updateOrderStatus(int id,String status);

    Order findOrderByOrderReferenceId(String id);

    void deleteOrderByProductId(long id);

    void deleteOrdersByProductCategoryId(int categoryId);
}
