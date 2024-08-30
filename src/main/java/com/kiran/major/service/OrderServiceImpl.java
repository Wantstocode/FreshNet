package com.kiran.major.service;

import com.kiran.major.dto.OrderRequest;
import com.kiran.major.dto.OrderStatus;
import com.kiran.major.model.Cart;
import com.kiran.major.model.Order;
import com.kiran.major.model.OrderAddress;
import com.kiran.major.model.User;
import com.kiran.major.repository.CartRepository;
import com.kiran.major.repository.OrderRepository;
import com.razorpay.*;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    @Autowired
    private RazorpayClient razorpayClient;



    @Transactional
    @Override
    public Order saveOnlinePaymentOrder(OrderRequest orderRequest, Integer userId) throws RazorpayException {
        List<Cart> cartList=cartRepository.findByUserId(userId);
        Order order1=new Order();
        double price=0;
        com.razorpay.Order razorPayOrder;

        for(Cart cart:cartList){
            price+=cart.getTotalPrice();
        }

        try {
            JSONObject razorPayOrderRequest = new JSONObject();

            razorPayOrderRequest.put("amount", price * 100);   //amount in paisa
            razorPayOrderRequest.put("currency", "INR");
            razorPayOrderRequest.put("receipt", orderRequest.getEmail());


            //create order in razorpay
            razorPayOrder = razorpayClient.orders.create(razorPayOrderRequest);
            order1.setRezorPayOrderId(razorPayOrder.get("id"));
            order1.setPrice(price);

        } catch (RazorpayException e) {
            throw new RuntimeException("Razorpay order creation failed", e);
        }

        for(Cart cart:cartList){

            //setting up order
            Order order=new Order();
            order.setOrderReferenceId("ORD-" + UUID.randomUUID().toString().substring(0, 18));
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

            order.setRezorPayOrderId(razorPayOrder.get("id"));
            orderRepository.save(order);

        }

        return order1;
    }

    @Transactional
    @Override
    public void saveOfflinePaymentOrder(OrderRequest orderRequest, Integer userId) {

        List<Cart> cartList=cartRepository.findByUserId(userId);
        for(Cart cart:cartList){

            //setting up order
            Order order=new Order();
            order.setOrderReferenceId("ORD-" + UUID.randomUUID().toString().substring(0, 18));
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

    @Transactional
    @Override
    public void deleteOrderByProductId(long id) {
        orderRepository.deleteOrderByProductId(id);
    }

    @Transactional
    @Override
    public void deleteOrdersByProductCategoryId(int categoryId) {
        orderRepository.deleteOrdersByProductCategoryId(categoryId);
    }


}
