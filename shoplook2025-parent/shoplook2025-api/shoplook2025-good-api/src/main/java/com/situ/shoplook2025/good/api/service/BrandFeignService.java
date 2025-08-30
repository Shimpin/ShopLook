package com.situ.shoplook2025.good.api.service;

import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.core.utils.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "shoplook2025-gateway",contextId = "brand-api")
public interface BrandFeignService {
    @GetMapping("/brand-api/api/v1/brands/id/{id}")
    ResponseEntity<JsonResult<Brand>> findById(@PathVariable Integer id);
}
