package com.situ.shoplook2025.good.api.service;

import com.situ.shoplook2025.category.model.Category;
import com.situ.shoplook2025.core.utils.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "shoplook2025-gateway",contextId = "category-api")
public interface CategoryFeignService {

    @GetMapping("/category-api/api/v1/categories/id/{id}")
    ResponseEntity<JsonResult<Category>> findById(@PathVariable Integer id);
}
