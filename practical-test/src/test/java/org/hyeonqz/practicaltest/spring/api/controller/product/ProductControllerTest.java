package org.hyeonqz.practicaltest.spring.api.controller.product;

import org.hyeonqz.practicaltest.spring.api.service.product.ProductService;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.hyeonqz.practicaltest.spring.domain.product.dto.req.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductController.class) // Controller 관련 bean 만 올릴 수 있는 테스트
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // mocking 처리를 도와주는 프레임워크

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean // productService mock 객체를 컨테이너에 넣어준다.
    private ProductService productService;

    @Test
    @DisplayName("신규 상품을 생성한다.")
    void createProduct () throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .productType(ProductType.HANDMADE)
            .productSellingType(ProductSellingType.SELLING)
            .name("Americano")
            .price(4000)
            .build();

        // when & then
        // api 를 쏜다.
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("")
    void ProductControllerTest() {
        // given

        // when

        // then
    }

}