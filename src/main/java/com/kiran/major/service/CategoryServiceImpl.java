package com.kiran.major.service;

import com.kiran.major.model.Category;
import com.kiran.major.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        List<Category> categories=categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category findById(int id) {
        Optional<Category> result = categoryRepository.findById(id);
        Category theCategory;

        if(result!=null) {
           theCategory = result.get();
            return theCategory;
        }
        else
            return null;
    }

    @Override
    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}
