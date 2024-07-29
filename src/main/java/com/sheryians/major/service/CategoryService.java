package com.sheryians.major.service;

import com.sheryians.major.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(int id);

    Category save(Category category);

    void deleteById(int id);
}
