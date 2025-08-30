package com.situ.shoplook2025.good.api.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.good.model.Good;
import com.situ.shoplook2025.good.model.search.GoodSearchBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    default Page<Good> findAll(Page<Good> page, GoodSearchBean gsb) {
        LambdaQueryWrapper<Good> qw = Wrappers.lambdaQuery(gsb);
        return selectPage(page, qw);
    }
}
