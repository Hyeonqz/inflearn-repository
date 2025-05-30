package org.hyeonqz.practicaltest.spring.domain.stock;

import org.hyeonqz.practicaltest.spring.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    @Builder
    public Stock (String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public static Stock create (String productNumber, int quantity) {
        return Stock.builder()
            .productNumber(productNumber)
            .quantity(quantity)
            .build();
    }

    public boolean isQuantityLessThan (int quantity) {
        return this.quantity < quantity;
    }

    public void deductQuantity (int quantity) {
        if(this.quantity < quantity)
            throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");

        this.quantity -= quantity;
    }

}
