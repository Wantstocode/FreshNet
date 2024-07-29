package com.kiran.major.service;

import com.kiran.major.model.Product;
import com.kiran.major.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(int pageNumber) {

        Pageable pageable= PageRequest.of(pageNumber-1,10);

        System.out.println("pageable"+ pageable);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAll(int pageNumber,String sortDir,String sortField) {

        Sort sort=Sort.by(sortField);
        sort=sortDir.equals("asc") ? sort.ascending() : sort.descending();


        Pageable pageable= PageRequest.of(pageNumber-1,10,sort);

        System.out.println("pageable"+ pageable);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findWithPaginationAndSorting( String keyword,int pageNumber,String sortDir,String sortField) {

        Sort sort=Sort.by(sortField);
        sort=sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable= PageRequest.of(pageNumber-1,10,sort);

        return productRepository.findWithPagination(keyword,pageable);
    }

    @Override
    public Page<Product> findWithPagination(String keyword,int pageNumber) {

        Pageable pageable= PageRequest.of(pageNumber-1,10);

        return productRepository.findWithPagination(keyword,pageable);
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
