스프링 카페 - DB에 저장하기 [Step-3]
===

# ✅ 구현 사항 체크리스트

## 1) H2 데이터베이스 연동

- [ ] h2 데이터베이스 의존성 추가하기
- [ ] Spring JDBC 사용하기
- [ ] DB 저장, 조회 SQL 작성하기

## 2) 게시글 데이터 저장하기
- [ ] Article 클래스를 DB 테이블에 저장하기
- [ ] Article 클래스 PK, FK 작성하기
  - PK : articleId
  - FK : userId

## 3) 게시글 목록 구현하기
- [ ] 전체 게시글 목록 데이터를 DB에서 조회하도록 구현
- [ ] Repository Interface 적용해보기
- [ ] @Primary 어노테이션 사용해보기

## 4) 게시글 상세보기 구현하기
- [ ] 게시글 세부 내용을 DB에서 가져오도록 구현한다
- [ ] 게시글 상세보기를 할 때, 조회수가 증가하도록 구현해본다
- [ ] Article 객체에 조회수를 담는 변수를 추가한다
  - Artciel Table에 추가를 할 지, 다른 테이블에 추가를 할 지 고민해보자

## 5) 사용자 정보 DB에 저장
- [ ] User 클래스를 DB 테이블에 저장하기

---

# DB 모델링하기
![img_1.png](img/img_3.png)

### ```Article```의 ```userId``` 를 ```외래키 [FK]```로 설정!
### ```User```에 하나의 ```userId```에 많은 수의 ```Article```이 올 수 있다.
- Many To One


