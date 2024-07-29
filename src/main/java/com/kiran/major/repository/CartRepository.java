package com.kiran.major.repository;

import com.kiran.major.model.Cart;
import com.kiran.major.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query("SELECT ci FROM Cart ci WHERE ci.user.id = :userId AND ci.product.id = :productId")
    public Cart findByProductIdAndUserId(@Param("userId") int userId, @Param("productId") Long productId);

    List<Cart> findByUserId(int id);

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.user.id = :userId")
    int countItemsByUserId(int userId);
}
