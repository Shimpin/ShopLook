package com.situ.shoplook2025.brand.api.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.brand.model.search.BrandSearchBean;
import com.situ.shoplook2025.brand.service.BrandService;
import com.situ.shoplook2025.core.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping(value = "/api/v1/brands", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrandApi {
    private BrandService brandService;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<JsonResult<?>> findAll(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, BrandSearchBean bsb) {
        Page<Brand> page = new Page<>(pageNo, pageSize);
        page = this.brandService.findAll(page, bsb);
        return ResponseEntity.ok(JsonResult.success(page));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JsonResult<?>> findById(@PathVariable Integer id) {
        Brand brand = this.brandService.findById(id);
        if (brand == null) {
            return ResponseEntity.ok(JsonResult.fail("未查询到指定编号的品牌"));
        } else {
            return ResponseEntity.ok(JsonResult.success(brand));
        }
    }

    @PostMapping
    public ResponseEntity<JsonResult<?>> save(@RequestBody Brand brand) {
        boolean success = this.brandService.save(brand);
        if (success) {
            return ResponseEntity.ok(JsonResult.success());
        } else {
            return ResponseEntity.ok(JsonResult.fail("保存品牌失败"));
        }
    }

    @PutMapping
    public ResponseEntity<JsonResult<?>> update(@RequestBody Brand brand) {
        boolean success = this.brandService.update(brand);
        if (success) {
            return ResponseEntity.ok(JsonResult.success());
        } else {
            return ResponseEntity.ok(JsonResult.fail("修改品牌失败"));
        }
    }

    @DeleteMapping
    public ResponseEntity<JsonResult<?>> deleteByIds(@RequestBody Integer[] ids) {
        int count = this.brandService.deleteByIds(List.of(ids));
        if (count == 0) {
            return ResponseEntity.ok(JsonResult.fail("删除品牌失败"));
        } else {
            return ResponseEntity.ok(JsonResult.success(count));
        }
    }
}
