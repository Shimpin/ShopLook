package com.situ.shoplook2025.good.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.good.model.Good;
import com.situ.shoplook2025.good.model.search.GoodSearchBean;

import java.util.List;

public interface GoodService {
    Page<Good> findAll(Page<Good> page, GoodSearchBean gsb);

    Good findById(Integer id);

    boolean save(Good good);

    boolean update(Good good);

    int deleteByIds(List<Integer> ids);
}
