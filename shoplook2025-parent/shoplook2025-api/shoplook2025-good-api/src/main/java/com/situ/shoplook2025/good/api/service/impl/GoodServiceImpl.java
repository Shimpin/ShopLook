package com.situ.shoplook2025.good.api.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.category.model.Category;
import com.situ.shoplook2025.core.utils.JsonResult;
import com.situ.shoplook2025.good.api.dao.GoodMapper;
import com.situ.shoplook2025.good.api.service.BrandFeignService;
import com.situ.shoplook2025.good.api.service.CategoryFeignService;
import com.situ.shoplook2025.good.model.Good;
import com.situ.shoplook2025.good.model.search.GoodSearchBean;
import com.situ.shoplook2025.good.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    private GoodMapper goodMapper;
    private CategoryFeignService categoryFeignService;
    private BrandFeignService brandFeignService;

    @Autowired
    public void setGoodMapper(GoodMapper goodMapper) {
        this.goodMapper = goodMapper;
    }

    @Autowired
    public void setCategoryFeignService(CategoryFeignService categoryFeignService) {
        this.categoryFeignService = categoryFeignService;
    }

    @Autowired
    public void setBrandFeignService(BrandFeignService brandFeignService) {
        this.brandFeignService = brandFeignService;
    }

    @Override
    public Page<Good> findAll(Page<Good> page, GoodSearchBean bsb) {
        Page<Good> result = goodMapper.findAll(page, bsb);
        result.getRecords().forEach(this::patch);
        return result;
    }

    private void patch(Good it) {
        ResponseEntity<JsonResult<Category>> resp = this.categoryFeignService.findById(it.getCategoryId());
        JsonResult<Category> jsonResult = resp.getBody();
        if (jsonResult != null) {
            it.setCategory(jsonResult.getData());
        }

        //品牌
        ResponseEntity<JsonResult<Brand>> resp1 = this.brandFeignService.findById(it.getBrandId());
        JsonResult<Brand> jsonResult1 = resp1.getBody();
        if (jsonResult1 != null) {
            it.setBrand(jsonResult1.getData());
        }
    }

    @Override
    public Good findById(Integer id) {
        Good good = goodMapper.selectById(id);
        patch(good);
        return good;
    }

    @Override
    public boolean save(Good Good) {
        return goodMapper.insert(Good) == 1;
    }

    @Override
    public boolean update(Good Good) {
        return goodMapper.updateById(Good) == 1;
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        return goodMapper.deleteByIds(ids);
    }
}
