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

### 미션5

- 로그인이 필요한 페이지에 접근 시 로그인 페이지로 이동
  - 기존 요청 URL을 기록
  - 로그인 후 위에서 기록한 URL로 이동

- 게시글에 수정, 삭제 버튼 추가
  - 수정, 삭제 버튼은 로그인한 아이디가 게시글의 작성자와 같은 경우에만 출력되도록 한다.
    - 추가로 요상한 요청이 들어갈 수 있으니, 로직에도 게시글작성자와 로그인사용자가 같은지 확인하는 부분을 넣어 리다이렉트 시킬 것.
  
- 게시글 수정
  - 기존 게시글 생성 페이지를 이용해서, PUT 요청을 보내면 될 것 같다.
    - 그 경로는 게시글의 articleId 로 하면 되겠다.
  - UPDATE articles SET title = ?, content = ? WHERE articleId = ?

- 게시글 삭제
  - articleId를 통해 삭제 명령을 보낸다.
  - DELETE FROM ARTICLES WHERE ARTICLEID = '123'; 이런 식으로?













