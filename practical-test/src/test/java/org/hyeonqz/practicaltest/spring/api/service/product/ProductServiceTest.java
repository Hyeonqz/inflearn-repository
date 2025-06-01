package org.hyeonqz.practicaltest.spring.api.service.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.hyeonqz.practicaltest.spring.api.service.product.response.ProductResponse;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.hyeonqz.practicaltest.spring.domain.product.dto.req.ProductCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void setUp () {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품 번호 +1 을 한다.")
    void createProduct() {
        // given
        Product product1 = this.createProduct("001",ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노",4000);
        Product product2 = this.createProduct("002",ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼",4500);
        Product product3 = this.createProduct("003",ProductType.HANDMADE, ProductSellingType.STOP_SELLING, "팥빙수",7000);
        productRepository.saveAll(List.of(product1,product2,product3));

        ProductCreateRequest request = ProductCreateRequest.builder()
            .productType(ProductType.HANDMADE)
            .productSellingType(ProductSellingType.SELLING)
            .name("카푸치노")
            .price(5000)
            .build();

        // when -> 대부분 1줄 행위를 가리킴.
        ProductResponse product = productService.createProduct(request);

        // then
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product)
            .extracting("productNumber","sellingType","productName","price")
            .contains("004", ProductSellingType.SELLING, "카푸치노", 5000);

        List<Product> all = productRepository.findAll();

    }

    @Test
    @DisplayName("상품이 하나도 없는 경우, 신규 상품을 등록하면 상품 번호는 001 이다.")
    void createProductWhenProductsIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .productType(ProductType.HANDMADE)
            .productSellingType(ProductSellingType.SELLING)
            .name("카푸치노")
            .price(5000)
            .build();

        // when -> 대부분 1줄 행위를 가리킴.
        ProductResponse product = productService.createProduct(request);

        // then
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product)
            .extracting("productNumber","sellingType","productName","price")
            .contains("001", ProductSellingType.SELLING, "카푸치노", 5000);
    }
    
    private Product createProduct(String productNumber, ProductType type, ProductSellingType sellingType, String name, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .productType(type)
            .sellingType(sellingType)
            .productName(name)
            .price(price)
            .build();
    }
}