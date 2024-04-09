스프링 카페 - 글 쓰기 기능 구현 [Step-2]
===

# ✅ 구현 사항 체크리스트

## 1) qna 페이지 작성
- [x] 글쓴이, 제목, 내용 3가지 부분이 들어갈 수 있도록 구성
  - 글쓴이 부분은 추후에 로그인 구현 시, Session이나 쿠키에서 가져올 수 있도록 구현

## 2) 글쓰기를 담당하는 ```ArticleController``` 구현
- [x] ```/qna```로 ```GET```요청이 들어오면 ```/qna/form.html```을 보여준다
- [ ] 질문 작성한 폼을 가지고 ```/qna/questions```로 ```POST```요청을 보낸다.
- [ ] ```/qna/questions```에 대한 POST 요청을 처리한다
- [ ] ```/``` 경로로 리다이렉트한다.

## 3) qna를 저장하는 DB와 Model 구현
- [ ] Article 객체 생성
    - 글쓴이, 제목, 내용, 생성 시간 [4개!]
- [ ] ArticleDatabse 데이터베이스 생성 [ArrayList로 생성]

## 4) qna 목록 출력하기
- [ ] Model에 ArrayList 객체를 넘겨서 처리한다
- [ ] Mustache의 부분 템플릿을 활용해서 arrayList를 순회하여 보여준다

## 5) 게시글 상세보기
- [ ] 게시글 제목을 클릭했을 때, 게시글 상세 피이지에 접속하도록 한다
  - ```<a>```태그를 이용해서 구현
> 게시글 상세 페이지 접근 URL은 "/articles/{index}"
> (예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.
- [ ] ```Article```객체에 ```id 인스턴스 변수``` 추가 
  - ```ArticleDatabase [ArrayList]```에 게시글 객체를 추가할 때, ```ArrayList.size() + 1```을 게시글 객체의 id로 사용!
- [ ] ```ArticleController```에 상세 페이지 접근 추가
    - ```@GetMaapping("/{id})``` 로 매핑
- [ ] 데이터 조회하기
  - ```ArticleDatabase```에서 ```index-1```에 해당하는 데이터를 조회하고 model에 전달





# 📜 URL Convention

| URL                 | 기능                         | 구현 상태 |
|:--------------------|:---------------------------|:-----:|
| GET /users          | 회원가입을 할 수 있는 폼을 보여준다       |  ⭕️   |
| POST /users         | 입력된 폼을 가지고, 회원 가입을 수행한다    |  ⭕️   |
| GET /users/list     | 회원가입된 유저들을 보여준다.           |  ⭕️   |
| GET /users/:userId  | useeId에 해당하는 profile을 보여준다 |    ⭕️   |
| GET /qna            | qna를 입력하는 폼을 보여준다          |       |
| POST /qna/questions | qna를 입력한 폼을 POST 요청으로 보낸다  |       |
| GET /               | 등록된 모든 qna를 보여준다           |       |

---
v