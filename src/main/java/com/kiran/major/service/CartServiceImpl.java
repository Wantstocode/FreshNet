package com.kiran.major.service;

import com.kiran.major.model.Cart;
import com.kiran.major.model.Product;
import com.kiran.major.model.User;
import com.kiran.major.repository.CartRepository;
import com.kiran.major.repository.ProductRepository;
import com.kiran.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{


    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart saveCart(long productId, Integer userId) {

        User user=userService.findById(userId);
        Product product=productService.findById(productId);

        Cart cartStatus=cartRepository.findByProductIdAndUserId(userId,productId);
        if(cartStatus==null) {
            Cart cart=new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setWeight(product.getWeight());
            cart.setTotalPrice(product.getPrice());
            System.out.println(cart.getId());
            System.out.println("hellow");
            return cartRepository.save(cart);
        }else{
//            cart = cartStatus;
            System.out.println(cartStatus.getId());
            System.out.println("hi");
            cartStatus.setQuantity(cartStatus.getQuantity()+1);

            cartStatus.setWeight( Double.parseDouble(String.format("%.2f",cartStatus.getWeight() + product.getWeight())));
            cartStatus.setTotalPrice(Double.parseDouble(String.format("%.2f",cartStatus.getTotalPrice() + product.getPrice())));

            return cartRepository.save(cartStatus);
        }

    }

    @Override
    public List<Cart> getByUserId(Integer id) {
        return cartRepository.findByUserId(id);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }
}
