package com.kiran.major.service;

import com.kiran.major.model.Cart;

import java.util.List;

public interface CartService {

    public Cart saveCart(long productId,Integer userId);

    public List<Cart> getByUserId(Integer id);

    void delete(Cart cart);
}
