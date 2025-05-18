package org.hyeonqz.practicaltest.spring.api.controller.order.reqeust;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    private List<String> productNumbers;

    @Builder
    public OrderCreateRequest (List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

}
