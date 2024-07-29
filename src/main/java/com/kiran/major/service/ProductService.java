package com.kiran.major.service;


import com.kiran.major.model.Product;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ProductService {


    Page<Product> findAll(int pageNumber);

    Page<Product> findAll(int pageNumber,String sortDir,String sortField);

    Page<Product> findWithPagination(String keyword,int pageNumber);

    Page<Product> findWithPaginationAndSorting(String keyword,int pageNumber,String sortDir,String sortField);

    Product findById(long id);

    List<Product> findByCategoryId(int id);


    Product save(Product product);

    void deleteById(long id);



}
