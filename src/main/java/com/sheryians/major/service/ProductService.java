package com.sheryians.major.service;

import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(long id);

    List<Product> findByCategoryId(int id);

    Product save(Product product);

    void deleteById(long id);

}
