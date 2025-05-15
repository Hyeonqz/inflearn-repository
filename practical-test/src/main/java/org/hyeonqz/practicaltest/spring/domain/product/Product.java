package org.hyeonqz.practicaltest.spring.domain.product;

import org.hyeonqz.practicaltest.spring.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductSellingType sellingType;

    private String productName;

    private int price;

    @Builder
    public Product (String productNumber, ProductType productType, ProductSellingType sellingType, String productName,
        int price) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.sellingType = sellingType;
        this.productName = productName;
        this.price = price;
    }

}
