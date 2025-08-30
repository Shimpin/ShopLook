package com.situ.shoplook2025.category.service;

import com.situ.shoplook2025.category.model.Category;

import java.util.List;

public interface CategoryService {
    //前端
    List<Category> findAll(Category category);

    //查询全部，非树型结构
    List<Category> findAll();

    //以树型结构的形式
    List<Category> findTree();

    //以树型结构返回
    Category findById(Integer id);

    boolean save(Category category);

    boolean update(Category category);

    int deleteById(Integer id);
}
