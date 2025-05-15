package org.hyeonqz.practicaltest.spring.api.service.product.response;

import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;

import lombok.Builder;

public class ProductResponse {

    private Long id;
    private String productNumber;
    private ProductType productType;
    private ProductSellingType sellingType;
    private String productName;
    private int price;

    @Builder
    private ProductResponse (Long id, String productNumber, ProductType productType, ProductSellingType sellingType,
        String productName, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.sellingType = sellingType;
        this.productName = productName;
        this.price = price;
    }

    public static ProductResponse of (Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .productNumber(product.getProductNumber())
            .productType(product.getProductType())
            .sellingType(product.getSellingType())
            .productName(product.getProductName())
            .price(product.getPrice())
            .build();
    }

    public Long getId () {
        return id;
    }

    public String getProductNumber () {
        return productNumber;
    }

    public ProductType getProductType () {
        return productType;
    }

    public ProductSellingType getSellingType () {
        return sellingType;
    }

    public String getProductName () {
        return productName;
    }

    public int getPrice () {
        return price;
    }

}
