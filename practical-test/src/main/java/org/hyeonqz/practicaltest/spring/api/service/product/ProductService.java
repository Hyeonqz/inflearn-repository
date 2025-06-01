package org.hyeonqz.practicaltest.spring.api.service.product;

import java.util.List;

import org.hyeonqz.practicaltest.spring.api.service.product.response.ProductResponse;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.dto.req.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  readOnly = true -> 읽기 전용
 *  -> CUD 작업 동작안함, Only Read
 *  JPA 이점이 있다? -> 스냅샷 사용하여 더티체킹을 한다. -> 스냅샷 저장을 하지 않고, 더티 체킹을 하지 않는다 -> 성능 향상 이점.
 *  CQRS? -> Command(CUD) / Read(Query) 를 분리하자. 빈도수는 Read 작업이 많다.. 책임을 의도적으로 분리를 하자. 서로 연관없게끔 하자.
 *  DB 분리에 편하다 -> Master(cud), Slave(read) -> readOnly ? -> slave db 사용, cud? -> master DB 사용, 즉 분기 처리가 가능하다.
 * */
@Transactional(readOnly = true) // 를 상단에 걸고 CUD 작업이 있다면 명시적으로 @Transactional 을 사용하자
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingTypeIn(ProductSellingType.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .toList();
    }

    // 동시성 이슈 발생 가능성 있음 -> Redis 분산락
    // id 값 Unique 걸고 재시도 로직3회 이상 걸기.
    // id 를 UUID 를 사용한다?
    @Transactional
    public ProductResponse createProduct (ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        if(latestProductNumber == null)
            return "001";

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumber = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumber);
    }

}
