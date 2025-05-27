package org.hyeonqz.practicaltest.spring.domain.product;

import static org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType.*;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductType.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllBySellingTypeInTest() {
        // given
        Product product = this.getProduct();

        Product product2 = this.getProduct2();

        Product product3 = this.getProduct3();

        productRepository.saveAll(List.of(product, product2, product3));

        // when
        List<Product> allBySellingTypeIn = productRepository.findAllBySellingTypeIn(List.of(SELLING, HOLD));

        // then
        Assertions.assertThat(allBySellingTypeIn).hasSize(2)
            .extracting("productNumber","productName","sellingType") // entity랑 같은 이름으로 해야함
            // 순서 상관없이 체크
            .containsExactlyInAnyOrder(
                Tuple.tuple("001","아메리카노",SELLING),
                Tuple.tuple("002","카페라떼",HOLD)
            );
            //.containsExactly() 순서까지 체크를 해줌
    }

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllByProductNumberInTest() {
        // given
        Product product = this.getProduct();

        Product product2 = this.getProduct2();

        Product product3 = this.getProduct3();

        productRepository.saveAll(List.of(product, product2, product3));

        // when
        List<Product> allByProductNumberIn = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        Assertions.assertThat(allByProductNumberIn).hasSize(2)
            .extracting("productNumber","productName","sellingType") // entity랑 같은 이름으로 해야함
            // 순서 상관없이 체크
            .containsExactlyInAnyOrder(
                Tuple.tuple("001","아메리카노",SELLING),
                Tuple.tuple("002","카페라떼",HOLD)
            );
        //.containsExactly() 순서까지 체크를 해줌
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    void findLatestProduct() {
        // given
        Product product = this.getProduct();

        Product product2 = this.getProduct2();

        Product product3 = this.getProduct3();

        productRepository.saveAll(List.of(product, product2, product3));

        // when
        String latestProduct = productRepository.findLatestProductNumber();

        // then
        Assertions.assertThat(latestProduct).isEqualTo("003");
    }

    private Product getProduct3 () {
        return Product.builder()
            .productNumber("003")
            .productType(HANDMADE)
            .sellingType(STOP_SELLING)
            .productName("카푸치노")
            .price(6000)
            .build();
    }

    private Product getProduct2 () {
        return Product.builder()
            .productNumber("002")
            .productType(HANDMADE)
            .sellingType(HOLD)
            .productName("카페라떼")
            .price(4500)
            .build();
    }

    private Product getProduct () {
        return Product.builder()
            .productNumber("001")
            .productType(HANDMADE)
            .sellingType(SELLING)
            .productName("아메리카노")
            .price(4000)
            .build();
    }

}