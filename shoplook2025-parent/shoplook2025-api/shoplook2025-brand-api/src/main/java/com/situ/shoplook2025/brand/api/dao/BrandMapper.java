package com.situ.shoplook2025.brand.api.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.brand.model.search.BrandSearchBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper extends BaseMapper<Brand> {
    default Page<Brand> findAll(Page<Brand> page, BrandSearchBean bsb) {
        LambdaQueryWrapper<Brand> qw = Wrappers.lambdaQuery(bsb);
        return selectPage(page, qw);
    }
}
