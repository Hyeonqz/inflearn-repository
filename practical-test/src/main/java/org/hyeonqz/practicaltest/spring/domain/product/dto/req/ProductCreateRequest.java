package org.hyeonqz.practicaltest.spring.domain.product.dto.req;

import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private ProductType productType;
    private ProductSellingType productSellingType;
    private String name;
    private int price;

    @Builder
    public ProductCreateRequest (ProductType productType, ProductSellingType productSellingType, String name,
        int price) {
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.name = name;
        this.price = price;
    }

}
