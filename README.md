# spring_practice
# Java Spring Study

## 0. 참고자료

- 인프런 - 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술
- 참고자료

    [스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술 v2021-03-03 (3).pdf](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/aac6c992-8dba-4084-89c9-42546d7eb71e/__-______MVC_DB___v2021-03-03_(3).pdf)

- 스프링 공식문서 [https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-static-content](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-static-content)

## 1. 자바 url & template 연동 방법

- resources → static → ~~.html을 정적으로 반환해줌
- 만약에 url에 [localhost:8080/~~가](http://localhost:8080/~~가) 들어오면
    1. controller에 들어가서 먼저 찾아봐 ~~를 받는게 있는지
    2. 없으면 resource/static 이나 public 이나 등등에서 찾아보고 그걸 반환해줌

## 2. MVC와 템플릿엔진

- Model, View, Controller
    - Controller는 Mapping 해줄 때 씀 (@Controller)
    - View → Resource/template/~~.html
    - Model : Domain에 저장되는 거 같음

## 3. API

- 객체로 반환하는 것을 나타냄
- @ResponseBody 이용
- **viewResolver대신 httpMessageConverter가 동작(추가 공부 필요)

## 4. 실제 비즈니스 서비스 구현해보기

- 구조
    1. 컨트롤러 : 웹 MVC의 컨트롤러 역할
    2. 서비스 : 핵심 비즈니스 로직 구현
    3. 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
    4. 도메인 : 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨(모델과 비슷한 개념인듯)

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8e44f79e-dc49-4664-bcdd-433ad609ac56/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8e44f79e-dc49-4664-bcdd-433ad609ac56/Untitled.png)

- 클래스 의존 관계

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e57d2e0f-8cc9-45f8-ab73-08efa7135e10/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e57d2e0f-8cc9-45f8-ab73-08efa7135e10/Untitled.png)

    나중에 Memory Member Repository 를 jdbc를 이용해 db로 연결시켜줄 수 있음. 나머지는 수정 안해도 됨. 이 연결부분만 수정시켜주면 됨.

## 실제 구현

- 회원 도메인 레포지토리
    - 레포지토리는 인터페이스로 구현하고, 이 인터페이스를 상속받는 것으로 구현.(상속받는 것은 Memory Member Repository가 됨 이것을 dbMemberRepository로 바꿔주면 됨) → 이래서 인터페이스를 사용하는 듯
- 구현 주의점
    1. Null 값이 발생할 여지가 있으면 Optional 사용 고려.

## 테스트케이스

- 테스트 케이스를 작성하면, 실제로 웹에 보여지지 않더라도 콘솔창에서 에러를 확인할 수 있음.
- 이 케이스를 꼼꼼히 잘 작성하는 것이 매우 중요(나중에 개발 시간의 60퍼정도는 이것을 작성하는데 쓰인다고 함.)
- @Test, @AfterEach, @BeforeEach
- 구조는 //given, //when, //then으로 나누어 작성하는 것이 편함.

## 스프링 빈 의존관계

- 이전 테스트에서는 개발자가 직접 주입했지만, 이번에는 빈과 @Autowired를 통해 자동으로 주입
- 스프링 빈을 등록하는 2가지 방법
    - 컴포넌트 스캔과 자동 의존관계 설정
    - 자바 코드로 직접 스프링 빈 등록하기
- @Controller를 이용하면 스프링 빈에 자동 등록됨. → 사실은 이 내부에 @Component때문.. 이걸 달아줘야 빈에 등록됨.
- @Controller, @Service, @Repository가 자동 등록될 수 있음.
- 생성자에 @Autowired를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서 주입. 생성자가 1개만 있으면 @Autowired는 생략가능.

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4822fa20-cb52-4039-b1e7-332d552bd8db/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4822fa20-cb52-4039-b1e7-332d552bd8db/Untitled.png)

### 자바 코드로 직접 스프링 빈 등록하기.

- 회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired 애노테이션을 제거하고
진행한다.

```java
package hello.hellospring;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SpringConfig {
 @Bean
 public MemberService memberService() {
 return new MemberService(memberRepository());
 }
 @Bean
 public MemberRepository memberRepository() {
return new MemoryMemberRepository();
 }
}
```

- 왜냐하면 Memory 리포지토리가 아닌 다른 리포지토리(jdbc)로 변경할 예정이라서, 컴포넌트 스캔 방식 대신 자바 코드로 스프링 빈을 직접 설정.

## 회원 관리 예제 - 웹 MVC 개발

- 홈 화면 추가 →  홈컨트롤러 + 홈 뷰
- html form tag → method "post" → 컨트롤러에서 postMapping을 이용해야함.
    - Get은 주로 조회
    - POST는 주로 정보 등록에 사용됨

## 스프링 DB접근 기술

## AOP 등록
