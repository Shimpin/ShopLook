package com.situ.shoplook2025.brand.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.brand.model.search.BrandSearchBean;

import java.util.List;

public interface BrandService {
    Page<Brand> findAll(Page<Brand> page, BrandSearchBean bsb);

    Brand findById(Integer id);

    boolean save(Brand brand);

    boolean update(Brand brand);

    int deleteByIds(List<Integer> ids);
}
