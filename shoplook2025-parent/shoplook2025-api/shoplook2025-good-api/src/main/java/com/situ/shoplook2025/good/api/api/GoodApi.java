package com.situ.shoplook2025.good.api.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.situ.shoplook2025.core.utils.JsonResult;
import com.situ.shoplook2025.good.model.Good;
import com.situ.shoplook2025.good.model.search.GoodSearchBean;
import com.situ.shoplook2025.good.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods", produces = MediaType.APPLICATION_JSON_VALUE)
public class GoodApi {
    private GoodService goodService;

    @Autowired
    public void setGoodService(GoodService goodService) {
        this.goodService = goodService;
    }
    
    @GetMapping
    public ResponseEntity<JsonResult<?>> findAll(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, GoodSearchBean gsb) {
        Page<Good> page = new Page<>(pageNo, pageSize);
        page = this.goodService.findAll(page, gsb);
        return ResponseEntity.ok(JsonResult.success(page));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JsonResult<?>> findById(@PathVariable Integer id) {
        Good good = this.goodService.findById(id);
        if (good == null) {
            return ResponseEntity.ok(JsonResult.fail("未查询到指定编号的商品"));
        } else {
            return ResponseEntity.ok(JsonResult.success(good));
        }
    }

    @PostMapping
    public ResponseEntity<JsonResult<?>> save(@RequestBody Good good) {
        boolean success = this.goodService.save(good);
        if (success) {
            return ResponseEntity.ok(JsonResult.success());
        } else {
            return ResponseEntity.ok(JsonResult.fail("保存商品失败"));
        }
    }

    @PutMapping
    public ResponseEntity<JsonResult<?>> update(@RequestBody Good good) {
        boolean success = this.goodService.update(good);
        if (success) {
            return ResponseEntity.ok(JsonResult.success());
        } else {
            return ResponseEntity.ok(JsonResult.fail("修改商品失败"));
        }
    }

    @DeleteMapping
    public ResponseEntity<JsonResult<?>> deleteByIds(@RequestBody Integer[] ids) {
        int count = this.goodService.deleteByIds(List.of(ids));
        if (count == 0) {
            return ResponseEntity.ok(JsonResult.fail("删除商品失败"));
        } else {
            return ResponseEntity.ok(JsonResult.success(count));
        }
    }
}
