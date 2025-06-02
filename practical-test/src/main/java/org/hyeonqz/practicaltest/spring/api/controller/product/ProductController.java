package org.hyeonqz.practicaltest.spring.api.controller.product;

import java.util.List;

import org.hyeonqz.practicaltest.spring.api.ApiResponse;
import org.hyeonqz.practicaltest.spring.api.service.product.ProductService;
import org.hyeonqz.practicaltest.spring.api.service.product.response.ProductResponse;
import org.hyeonqz.practicaltest.spring.domain.product.dto.req.ProductCreateRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(productService.getSellingProducts());
    }

    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(request));
    }

}
