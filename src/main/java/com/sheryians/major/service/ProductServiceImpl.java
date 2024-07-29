package com.sheryians.major.service;

import com.sheryians.major.model.Product;
import com.sheryians.major.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        Optional<Product> result = productRepository.findById(id);
        Product product;

        if(result!=null) {
            product = result.get();
            return product;
        }
        else
            return null;
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        return productRepository.findAllByCategoryId(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }
}
