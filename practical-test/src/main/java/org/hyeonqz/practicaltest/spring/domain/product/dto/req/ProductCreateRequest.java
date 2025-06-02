package org.hyeonqz.practicaltest.spring.domain.product.dto.req;

import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.") // enum -> notnull 사용
    private ProductType productType;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingType productSellingType;

    //@NotNull // 공백 " " 은 처리하지 못함
    //@NotEmpty // 공백은 통과, " " 은 통과하지 못함
    //@Max(message = "최대 50자리 까지만 가능합니다.", value = 50)
    @NotBlank(message = "상품 이름은 필수입니다.") // String -> NotBlank @NotNull + @NotEmpty 종합본임
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.") // int,long -> @Positive -> 양수 인지 체크
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
