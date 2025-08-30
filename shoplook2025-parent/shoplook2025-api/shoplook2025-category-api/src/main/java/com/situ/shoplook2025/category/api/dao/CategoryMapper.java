package com.situ.shoplook2025.category.api.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.situ.shoplook2025.category.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    default List<Category> findAll(Category category) {
        LambdaQueryWrapper<Category> qw = Wrappers.lambdaQuery(category);
        return selectList(qw);
    }
}
