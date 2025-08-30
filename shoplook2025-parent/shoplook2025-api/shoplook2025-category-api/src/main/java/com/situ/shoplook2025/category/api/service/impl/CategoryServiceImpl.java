package com.situ.shoplook2025.category.api.service.impl;

import com.situ.shoplook2025.category.api.dao.CategoryMapper;
import com.situ.shoplook2025.category.model.Category;
import com.situ.shoplook2025.category.service.CategoryService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "shoplook2025-category")
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findAll(Category category) {
        return categoryMapper.findAll(category);
    }

    @Cacheable(key = "'all'")
    @Override
    public List<Category> findAll() {
        return categoryMapper.selectList(null);
    }

    @Cacheable(key = "'tree'")
    //树型结构
    @Override
    public List<Category> findTree() {
        //使用代理调用，可以触发aop
        List<Category> categories = proxy().findAll();
        return makeTree(categories);
    }

    @Cacheable(key = "#id")
    //树型结构
    @Override
    public Category findById(Integer id) {
        //使用代理调用，可以触发aop
        List<Category> roots = proxy().findTree();
        return findById(roots, id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean save(Category category) {
        return this.categoryMapper.insert(category) == 1;
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean update(Category category) {
        return this.categoryMapper.updateById(category) == 1;
    }

    @CacheEvict(allEntries = true)
    @Override
    public int deleteById(Integer id) {
        //使用代理调用，可以触发aop
        Category cat = proxy().findById(id);//树型结构
        if (cat.getChildren() != null) {
            throw new UnsupportedOperationException("当前分类尚有子分类，无法删除");
        }
        return this.categoryMapper.deleteById(id);//物理删除
    }

    //生成树型结构
    private List<Category> makeTree(List<Category> categories) {
        Map<Integer, Category> cache = new HashMap<>();
        for (Category category : categories) {
            cache.put(category.getId(), category);
        }

        List<Category> roots = new ArrayList<>();
        //遍历所有分类
        for (Category category : cache.values()) {
            if (category.getParentId() == null) {
                roots.add(category);
            } else {
                Category parent = cache.get(category.getParentId());
                if (parent == null) {
                    throw new RuntimeException("找不到指定编号的分类");
                }
                //设置关联
                category.setParent(parent);
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(category);
            }
        }

        return roots;
    }

    //在一个分类集合中找指定编号的分类
    private Category findById(List<Category> categories, Integer id) {
        for (Category cat : categories) {
            if (cat.getId().equals(id)) {
                return cat;
            }
            if (cat.getChildren() != null) {
                Category found = findById(cat.getChildren(), id);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    //获取当前类的代理实现
    private CategoryService proxy() {
        return (CategoryService) AopContext.currentProxy();
    }
}
