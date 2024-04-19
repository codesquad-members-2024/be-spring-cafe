# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페

### 궁금증

- List를 통해서 반환하는 articleList를 깊은 복사를 통해 반환해야 하는가?

    단순히 Model을 이용해서 뷰처리를 하는데에 사용되는 경우에는 괜찮아보인다.

- Article을 저장하는 자료구조가 고민

    등록된 순서가 보장되어서 역순으로 꺼내면 가장 최근의 것들부터 가져올 수 있으면 좋겠다.
    그리고 Concurrency 문제도 해결할 수 있으면 좋겠다.

    - LinkedHashMap, ConcurrentSkipListMap


### 처리할 문제

- [x] 중복된 id를 갖는 회원가입처리

- [ ] 게시글을 확인할 때 개행이 처리되지 않는다.

- [x] 게시글에 시간정보를 추가해야 한다.

**- [ ] 회원id로 검색 시, 실패하는 경우에 대한 처리가 필요하다.
  - User 대신 `null`이 반환되게 되는데, 이걸 어떻게 할 수 있지?**

- [x] Article의 작성시간을 유지시켜야한다.

- [ ] 메인페이지 말고 다른 페이지에서는 왜 로그인하지 않은 유저가 되는걸까....
  - 세션 유지가 안된다?
  - 세션은 유지가 되지만 매핑에 따라 loginUserId 어트리뷰트를 추가하지 않아서? 

- [ ] subnav에 대한 예쁜 버튼 찾기
---

http://repository.transtep.com/repository/thirdparty/H2/1.0.63/docs/html/datatypes.html#sql141

---

### 비밀번호 확인 로직

비밀번호에 대한 정보는 DB에 저장되어 있다.

입력은 User 로 온다. 