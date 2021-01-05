

# Project

> 쿠폰 API 구현 [서버사전과제2] <br>
> 작성자: 노지영 <kkj01331@gmail.com>
## 프로젝트 요구사항
- Rest API 기반으로 사용자에게 할인, 선물 등 쿠폰을 제공하는 서비스를 개발
- 제공된 기능명세에 대한 쿠폰시스템 API를 개발하고 Test 코드를 작성
## 개발 환경
Language: **java** openjdk 1.8    
Framework: **Spring boot** 2.1.7.RELEASE    
Data: **Spring Data JPA, h2** <br>
Build: gradle 6.7  
Test: Junit   
doc: Swagger2  
IDE: IntelliJ IDEA

# Getting Started
Project clone > cmd > 해당 폴더로 이동 > 명령어 입력
1. buid  
   `> gradlew  `
2. test
   `> gradlew test`
3. run    
   `> gradlew bootRun `
4. local swagger page  
   `http://localhost:8080/swagger-ui.html`

# Detail
> main.java.com.kakaopay.homework.web.CouponController.java [API] <br>
> test.java.com.kakaopay.homework.web.CouponControllerTest.java [Test]


```
쿠폰번호: XXXXX-XXXXXX-XXXXXXXX 
> ex) K0103-160005-48973639  
> "K" + MMdd-HHmmss- + random 8자  
```
