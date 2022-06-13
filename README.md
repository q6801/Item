# Item
[API 테스트 페이지 바로가기](https://documenter.getpostman.com/view/17090337/Uz5Njtbv)

### 개발 목표
활용 가능한 언어로 상품정보 서비스를 제공하는 API 서버 구축

### 사용 도구
- java 1.8
- Spring Boot 2.7.0
- JPA
- Junit 5
- Lombok
- Gradle
- H2 database

---
### Entity

ITEM
- ITEM의 이름이 동일하더라도 중복 검사는 따로 하지 않는다. (같은 이름을 가진 상품이 존재할 수 있기 때문)
- 전시 종료 날짜가 전시 시작 날짜보다 이전일 수는 없다.
```
id : item의 식별자로 Auto Increment를 사용
name : item의 이름은 100글자보다 작거나 같다.
itemType : (일반, 기업회원상품)으로 구성되어 있다.
itemPrice : 상품의 가격
itemDisplayStartDate : 상품 전시 시작 날짜이다.
itemDisplayEndDate : 상품 전시 종료 날짜이다.
```
---

PROMOTION
- PROMOTION의 이름이 동일하더라도 중복 검사는 따로 하지 않는다. (같은 이름을 가진 프로모션이 존재할 수 있기 때문)
- discountAmount와 discountRate는 둘 다 null이 될 수 없다.
- discountAmount와 discountRate는 동시에 값이 있을 수 있다. 추후에 할인을 적용할 때는 할인된 금액이 0보다 크며 가장 작은 금액을 할인된 금액으로 사용한다.
- 프로모션 종료 날짜가 프로모션 시작 날짜보다 이전일 수는 없다.
```
id : promotion의 식별자로 Auto Increment를 사용
name : promotion의 이름은 100글자보다 작거나 같다.
discountAmount : null이거나 자연수이다.
discountRate : null이거나 0 초과 1미만의 소수이다.
promotionStartDate : 프로모션 시작 날짜이다.
promotionEndDate : 프로모션 종료 날짜이다.
```
---
USER
- USER의 이름이 동일하더라도 중복 검사는 따로 하지 않는다. (같은 이름을 가진 유저가 존재할 수 있기 때문)
```
id : user의 식별자로 Auto Increment를 사용
name : user의 이름은 30글자보다 작거나 같다.
userType : (일반, 기업회원) 중 하나이다.
userStat : (정상, 탈퇴) 중 하나이다.
```
---
MAP-PROMOTION-ITEM
- 프로모션과 item을 매핑하는 매핑 테이블이다.
```
id : promotion의 식별자로 Auto Increment를 사용
item : 매핑할 item
promotion : 매핑할 promotion
```
---

### 문제 해결 방법
- entity class와 입, 출력 DTO를 분리
    - 사용자의 필요한 응답에 대응이 쉬운 DTO를 입출력에 사용
- 유저 이름, item 이름, 프로모션 이름은 중복 검사가 불가능하다
    - 동명이인같이 공통된 이름과 속성을 지닐수도 있기 떄문
- 프로모션 할인 정보는 정액 정률 두가지가 존재하고, 둘 중에 한가지만 적용됨
    - 할인 정보가 둘 다 있는 경우는 있지만, 하나도 없는 경우는 없다.
    - 할인된 가격이 0초과이면서 가장 작은 값을 만든 프로모션의 할인 정책이 선택된다.
- 관계 테이블에서의 삭제
    - 서로 관계가 원래 없더라도 삭제결과는 success를 반환한다.
    - 일반적인 객체 삭제의 경우 객체가 없으면 오류가 반환되는데 이것은 관계 테이블에서 관계를 지워주는 것에 의의가 있으니 
      목표 promotion과 item의 관계가 원래 없어도 SUCCESS가 나오도록 설계하였다.
- JPA와 db 호출
    - 특정 item에 종속된 promotion중 display의 날짜가 현재 날짜에 해당하는 것만 가져와야한다.
    - 방법 1 : jpa의 연관 객체로 display 날짜가 맞는 것 선별
    - 방법 2 : db에서 특정 item에 종속되며 display 날짜가 맞는 것 선별
    - 결론
        - 객체지향적인 방법은 1번이지만 item에 속한 promotion의 숫자가 많아진다면 그만큼 db에서 데이터 호출 후 
          선별 작업이 spring 서버에서 진행되어서 비효율적이 된다.
        - 즉, item에 속한 promotion의 숫자에 따라서 선택이 필요하다.
        - 지금은 방법 1을 사용중하여 구현하였다.
    
    