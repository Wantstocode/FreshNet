package com.kiran.major.service;


import com.kiran.major.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(int id);

    Category save(Category category);

    void deleteById(int id);
}
