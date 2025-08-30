package com.situ.shoplook2025.brand.api.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.brand.api.dao.BrandMapper;
import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.brand.model.search.BrandSearchBean;
import com.situ.shoplook2025.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "shoplook2025-brands")
public class BrandServiceImpl implements BrandService {
    private BrandMapper brandMapper;

    @Autowired
    public void setBrandMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Override
    public Page<Brand> findAll(Page<Brand> page, BrandSearchBean bsb) {
        return brandMapper.findAll(page, bsb);
    }

    @Cacheable(key = "#id")
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectById(id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean save(Brand brand) {
        return brandMapper.insert(brand) == 1;
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean update(Brand brand) {
        return brandMapper.updateById(brand) == 1;
    }

    @CacheEvict(allEntries = true)
    @Override
    public int deleteByIds(List<Integer> ids) {
        //todo:如果品牌被商品使用，则不能删除
        return brandMapper.deleteByIds(ids);
    }
}
