package org.hyeonqz.practicaltest.spring.domain.product;

import java.util.Collection;
import java.util.List;

import org.hyeonqz.practicaltest.spring.api.service.product.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /* select * from product where selling_type in ('SELLING', 'HOLD') */
    List<Product> findAllBySellingTypeIn(List<ProductSellingType> sellingType);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    @Query(value = "select p.product_number from product p order by p.id desc limit 1", nativeQuery = true)
    String findLatestProductNumber ();

}
