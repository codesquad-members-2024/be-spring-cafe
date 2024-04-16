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

중복된 id를 갖는 회원가입처리

게시글을 확인할 때 개행이 처리되지 않는다.