package org.hyeonqz.practicaltest.spring.domain.product;

import java.util.Collection;
import java.util.List;

import org.hyeonqz.practicaltest.spring.api.service.product.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /* select * from product where selling_type in ('SELLING', 'HOLD') */
    List<Product> findAllBySellingTypeIn(List<ProductSellingType> sellingType);
}
