# Practical Test - 실용적인 테스트 가이드

## 학습 내용
- 테스트 코드가 필요한 이유
- 좋은 테스트 코드란 무엇일까?
- 실제 실무에서 진행하는 방식 그대로 테스트를 작성해가면서 API를 설계하고 개발하는 방법
- 구체적인 이유에 근거한 상세헌 테스트 작성 팁

테스트 코드를 작성함으로써 얻을 수 있는 것
- 팀원간 빠른 피드백
- 테스트 자동화
- 어플리케이션 안정감


### 테스트 코드를 작성하지 않는다면?
- 변화가 생기는 매순간마다 발생할 수 있는 모든 Case 고려
- 변화가 생기는 매순간마다 모든 팀원이 동일한 고민을 해야함
- 빠르게 변화하는 소프트웨어의 안정성으루 보장할 수 없음


### 반면 테스트 코드가 병목이 된다면?
- 프로덕션 코드의 안정성을 제공하기 힘듬
- 테스트 코드 자체가 유지보수하기 어려운, 새로운 짐이 된다. -> 안짜는게 나을 수도..
- 잘못된 검증이 이루어질 가능성이 생긴다.

### 그렇다면 올바른 테스트 코드는?
- 자동화 테스트로 비교적 빠른 시간안에 버그를 발견할 수 있고, 수동 테스트에 드는 비용을 크게 절약할 수 있다.
- 소프트웨어의 빠른 변화를 지원한다.
- 팀원들의 집단 지성을 팀 차원의 이익으로 승격시킨다.
- 가까이 보면 느리지만, 멀리 보면 가장 빠른것이 테스트 이다.

결론은 테스트는 귀찮지만, 해야한다 <br><br>


### 요구사항
- 주문 목록에 음료 추가/삭제 기능
- 주문 목록 전체 지우기
- 주문 목록 총 금액 계산하기
- 주문 생성하기

#### 수동 테스트 vs 자동화된 테스트
수동 테스트? -> System.out.println() 을 통한 사람이 직접 값 체크 <br>
자동화 테스트? -> Junit5 를 사용하여 테스트 주체를 기계로 하여 검증을 해준다 <br>

### 단위 테스트
- 작은 코드 단위(클래스,메소드) 를 독립적으로 검증하는 테스트
  - 독립적? -> 외부의 개입을 받지 않는다 Ex) repository, 외부 api 호출
- 검증 속도가 빠르고, 안정적이다

어떻게 테스트를 할까? -> Junit5, AssertJ 사용 <br>
- AssertJ -> 풍부한 API, 메소드 체이닝 지원 <br>

#### 테스트 케이스 세분화하기
1. 해피 케이스
2. 예외 케이스

어떠한 경계값이 존재한다면, 경계값 테스트가 필요하다 ex) 범위, 구간, 날짜 <br>
```java
	@Test
    void add_count_throw_test() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    Assertions.assertThatThrownBy(() -> cafeKiosk.add(americano,0))
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }
```

위 코드는 일반적인 경계값 예외 케이스 테스트 이다. <br>


### 테스트 하기 어려운 영역?
- 관측할 때 마다 다른 값에 의존하는 코드
  - 현재 날짜/시간, 랜덤 값
- 외부 세계에 영향을 주는 코드
  - 표준 출력, 메시지 발송, 데이터베이스에 기록하기 등

테스트를 쉽게하려면?
- 같은 입력에는 항상 같은 결과
- 외부 세상과 단절된 형태
- 테스트하기 쉬운 코드


### TDD(Test Driven Development)
- RED - GREEN - REFACTOR (레드-그린-리팩토링)

애자일 방법론 안에 익스트림 프로그래밍을 실천할 때 TDD 를 주로 사용한다 <br>

### 테스트는 '문서' 다
- 프로덕션 기능을 설명하는 테스트 코드 문서
- 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완
- 어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격시켜서, 모두의 자산으로 공유할 수 있다.

-> 우리는 항상 팀으로 일한다, '소스 코드' 는 회사의 자산이다 <br>

> 1. DisplayName 을 섬세하게 작성하자
>    - ex) 음료를 1개 추가하면 주문 목록에 담긴다. ~테스트 라는 문장은 지양하자. 테스트 결과 까지 명시하면 더 좋다.
>    - 도메인 용어를 사용하여 한층 추상화된 내용을 담자 Ex) '영업 시작 시간' 이전에는 주문을 생성할 수 없다.
> 2. BDD 스타일로 작성하기
>    - given, when, then 사용
>    - 함수 단위의 테스트에 집중하기 보다, 시나리오에 기반한 테스트케이스 자체에 집중하여 테스트 한다.
>    - 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준을 권장


### 언어가 사고를 제한하게 하지 말자.
잘못 작성한 테스트는 오히려 허들이 되고, 사고를 제한하게 할 수 있다 <br>


## Spring & JPA 기반 테스트
레이어드 아키텍처를 사용하는 이유? -> '관심사의 분리' 가 주된 목적이다 <br>

테스트 하기 어려운 부분을 분리해서 테스트 하고자 하는 영역에 집중하자 <br>

단위 테스트 만으로는 커버하기 어려운 부분이 생긴다, 그러므로 통합 테스트가 필요하다 <br>

### 통합 테스트
- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
- 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없다.
- 풍부한 단위 테스트 -> 큰 기능을 검증하는 통합 테스트가 필요하다.

jpa N:N 관계시 중간 매핑 테이블을 둬서 관리하는게 좋다 <br>

Entity 에서 상속받을 baseEntity 는 인스턴스를 만들 필요가 없기에 추상 클래스로 만든다
```java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  private LocalDateTime createdDateTime;

  @LastModifiedDate
  private LocalDateTime updatedDateTime;
}

// 추가적으로 메인 어플리케이션에  
// @EnableJpaAuditing  어노테이션을 선언해야 한다.
```

## Persistence Layer
- Data Access 역할
- 비즈니스 가공 로직이 포함되어서는 안 된다.
  - Data -> CRUD 에만 집중 해야함.

### Repository Test
단위 테스트에 가까운 테스트 이다 <br>
어떻게 보면 스프링 서버를 띄우지만, db connection 부분만 띄우기 때문에 단위 테스트 성격을 가진다 <br>

```java
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllBySellingTypeInTest () {
        // given
        Product product = this.getProduct();

        Product product2 = this.getProduct2();

        Product product3 = this.getProduct3();

        productRepository.saveAll(List.of(product, product2, product3));

        // when
        List<Product> allBySellingTypeIn = productRepository.findAllBySellingTypeIn(List.of(SELLING, HOLD));

        // then
        Assertions.assertThat(allBySellingTypeIn).hasSize(2)
            .extracting("productNumber", "productName", "sellingType") // entity랑 같은 이름으로 해야함
            // 순서 상관없이 체크
            .containsExactlyInAnyOrder(
                Tuple.tuple("001", "아메리카노", SELLING),
                Tuple.tuple("002", "카페라떼", HOLD)
            );
        //.containsExactly() 순서까지 체크를 해줌
    }
}
```

@SpringBootTest 보다 가볍다 <br>

## Business Layer
- 비즈니스 로직을 구현하는 역할
- Persistence Layer 와 의 상호작용을 통해 비즈니스 로직을 전개시킨다.
- '트랜잭션' 을 보장해야 한다.

Business Layer 테스트는 Persistence Layer 와 두개를 통합하는 테스트를 진행한다 <br>

LocalDateTime 같은 경우는 파라미터를 통해서 받는 식으로 로직을 작성해나가는 것이 좋다 <br>

@DataJpaTest 는 내부적으로 @Transactional 이 달려있어 1개의 테스트가 끝나면 자동으로 롤백이 된다 <br>
@SpringBootTest 는 @Transactional 이 없어 자동으로 롤백이 되지않는다. 롤백이 필요하면 @Transactional 을 사용하자 <br>


## Presentation Layer
- 외부 요청을 가장 먼저 받는 계층
  - 스프링 사용시 filter, interceptor 가 앞단에서 먼저 요청을 받을 수 있다.
- 파라미터에 대한 최소한의 검증을 수행한다.

위 계층에서는 Mocking 을 통하여 테스트를 진행한다 <br>

```java
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
  void createProduct() throws Exception {
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

}
```

#### DTO 에서 spring validation 을 통해 검증하는 방법
```java
@NoArgsConstructor
@Getter
public class ProductCreateRequest {

  @NotNull(message = "상품 타입은 필수입니다.") // enum -> notnull 사용
  private ProductType productType;

  @NotNull(message = "상품 판매상태는 필수입니다.")
  private ProductSellingType productSellingType;

  @Max(message = "최대 50자리 까지만 가능합니다.", value = 50)
  @NotBlank(message = "상품 이름은 필수입니다.") // String -> NotBlank 사용
  private String name;

  @Positive(message = "상품 가격은 양수여야 합니다.") // int,long -> @Positive -> 양수 인지 체크
  private int price;
}

@PostMapping("/api/v1/products/new")
public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
  return ApiResponse.ok(productService.createProduct(request));
}
```

```java
@Getter
public class ApiResponse<T> {

    private int code;
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public ApiResponse (HttpStatus httpStatus, String message, T data) {
        this.code = httpStatus.value();
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return new ApiResponse<>(httpStatus, httpStatus.name(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.name(), data);
    }
}
```

API 공통 응답형식 정의 하는 코드이다 <br>

테스트는 아래와 같이 검증한다 <br>
```java
    @Test
    @DisplayName("신규 상품 등록시 상품 타입은 필수값이다.")
    void createProductWithoutType () throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .productType(ProductType.HANDMADE)
            .name("Americano")
            .price(4000)
            .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            // getter 가 있어야 함.
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 판매상태는 필수입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty()
            );
    }
```

컨트롤러에서 Validation 을 체크할 때 '**책임을 분리**' 할 수 있어야 한다 <br>

```java
    //@Max(message = "최대 50자리 까지만 가능합니다.", value = 50)
    @NotBlank(message = "상품 이름은 필수입니다.") // String -> NotBlank @NotNull + @NotEmpty 종합본임
    private String name;
```

@Max 같은 경우는 도메인 정책이기 때문에 따로 Validation 을 해야한다. <br>

#### queryParameter 검증
```java
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/selling")
                .queryParam("name","hkjin")
                .queryParam("id","14")
            )
```

#### Parameter 가 없는 @GetMapping 테스트
```java
    @Test
    @DisplayName("판매중인 상품을 조회한다.")
    void getSellingProducts () throws Exception {
        // given & when
        Mockito.when(productService.getSellingProducts()).thenReturn(List.of());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/selling")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        ;
    }
```

