package com.kiran.major.repository;

import com.kiran.major.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, PagingAndSortingRepository<Product,Long>{

    List<Product> findAllByCategoryId(int id);

    @Query("select p from Product p where p.name like %:keyword% or p.category.name like %:keyword% or cast(p.price as string) like %:keyword%")
    Page<Product> findWithPagination(String keyword, Pageable pageable);

}