package com.kiran.major.service;

import com.kiran.major.dto.OrderRequest;
import com.kiran.major.dto.OrderStatus;
import com.kiran.major.model.Cart;
import com.kiran.major.model.Order;
import com.kiran.major.model.OrderAddress;
import com.kiran.major.model.User;
import com.kiran.major.repository.CartRepository;
import com.kiran.major.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    @Override
    public void saveOrder(OrderRequest orderRequest, Integer userId) {
        List<Cart> cartList=cartRepository.findByUserId(userId);
        for(Cart cart:cartList){

            //setting up order
            Order order=new Order();
            order.setOrderReferenceId(UUID.randomUUID().toString());
            order.setOrderDate(new Date());
            order.setProduct(cart.getProduct());
            order.setPrice(cart.getTotalPrice());
            order.setQuantity(cart.getQuantity());
            order.setUser(cart.getUser());
            order.setOrderStatus(OrderStatus.IN_PROGRESS.getName());
            order.setPaymentType(orderRequest.getPaymentType());

            //setting up order address
            OrderAddress orderAddress=new OrderAddress();
            orderAddress.setFirstName(orderRequest.getFirstName());
            orderAddress.setLastName(orderRequest.getLastName());
            orderAddress.setEmail(orderRequest.getEmail());
            orderAddress.setMobileNo(orderRequest.getMobileNo());
            orderAddress.setAddress(orderRequest.getAddress());
            orderAddress.setCity(orderRequest.getCity());
            orderAddress.setPinCode(orderRequest.getPinCode());
            orderAddress.setAdditionalInfo(orderRequest.getAdditionalInfo());

            order.setOrderAddress(orderAddress);

            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findOrderByUser(User user) {
        return orderRepository.findOrderByUser(user);
    }

    @Override
    public boolean cancelOrder(int id) {
        Optional<Order> order=orderRepository.findById(id);
        if(order.isPresent()){
            Order order1=order.get();
            order1.setOrderStatus(OrderStatus.ORDER_CANCEL.getName());
            orderRepository.save(order1);
            return true;
        }
        return false;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public boolean updateOrderStatus(int id, String status) {
        Optional<Order> order=orderRepository.findById(id);
        if(order.isPresent()){
            Order order1=order.get();
            order1.setOrderStatus(status);
            orderRepository.save(order1);
            return true;
        }
        return false;
    }

    @Override
    public Order findOrderByOrderReferenceId(String id) {
        return orderRepository.findOrderByOrderReferenceId(id);
    }
}
