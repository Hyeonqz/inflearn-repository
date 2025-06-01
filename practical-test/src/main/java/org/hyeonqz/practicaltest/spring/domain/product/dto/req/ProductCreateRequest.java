package org.hyeonqz.practicaltest.spring.domain.product.dto.req;

import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    @NotNull // enum -> notnull 사용
    private ProductType productType;

    @NotNull
    private ProductSellingType productSellingType;

    @NotBlank // String -> NotBlank 사용
    private String name;

    @Positive // int,long -> @Positive -> 양수 인지 체크
    private int price;

    @Builder
    public ProductCreateRequest (ProductType productType, ProductSellingType productSellingType, String name,
        int price) {
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.name = name;
        this.price = price;
    }

    public Product toEntity (String nextProductNumber) {
        return Product.builder()
            .productNumber(nextProductNumber)
            .productType(productType)
            .sellingType(productSellingType)
            .productName(name)
            .price(price)
            .build();
    }

}
