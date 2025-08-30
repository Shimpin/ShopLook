package com.situ.shoplook2025.category.api.api;

import com.situ.shoplook2025.category.model.Category;
import com.situ.shoplook2025.category.service.CategoryService;
import com.situ.shoplook2025.core.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryApi {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<JsonResult<?>> findAll(Category category) {
        List<Category> cats = this.categoryService.findAll(category);
        return ResponseEntity.ok(JsonResult.success(cats));
    }

    @GetMapping("/all")
    public ResponseEntity<JsonResult<?>> findAll() {
        List<Category> cats = this.categoryService.findAll();
        return ResponseEntity.ok(JsonResult.success(cats));
    }

    @GetMapping("/tree")
    public ResponseEntity<JsonResult<?>> findTree() {
        List<Category> cats = this.categoryService.findTree();
        return ResponseEntity.ok(JsonResult.success(cats));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JsonResult<?>> findById(@PathVariable Integer id) {
        Category cat = this.categoryService.findById(id);
        if (cat == null) {
            return ResponseEntity.ok(JsonResult.fail("未查询到指定编号的分类"));
        } else {
            return ResponseEntity.ok(JsonResult.success(cat));
        }
    }

    @PostMapping
    public ResponseEntity<JsonResult<?>> save(@RequestBody Category cat) {
        boolean success = this.categoryService.save(cat);
        if (success) {
            return ResponseEntity.ok(JsonResult.success());
        } else {
            return ResponseEntity.ok(JsonResult.fail("保存分类失败"));
        }
    }

    @PutMapping
    public ResponseEntity<JsonResult<?>> update(@RequestBody Category cat) {
        boolean success = this.categoryService.update(cat);
        if (success) {
            return ResponseEntity.ok(JsonResult.success());
        } else {
            return ResponseEntity.ok(JsonResult.fail("修改分类失败"));
        }
    }

    @DeleteMapping
    public ResponseEntity<JsonResult<?>> deleteByIds(@RequestBody Integer[] ids) {
        int count = 0;
        for (Integer id : ids) {
            count += this.categoryService.deleteById(id);
        }

        if (count == 0) {
            return ResponseEntity.ok(JsonResult.fail("删除分类失败"));
        } else {
            return ResponseEntity.ok(JsonResult.success(count));
        }
    }
}
