스프링 카페 - 글 쓰기 기능 구현 [Step-2]
===

# ✅ 구현 사항 체크리스트

## 1) qna 페이지 작성

- [x] 글쓴이, 제목, 내용 3가지 부분이 들어갈 수 있도록 구성
    - 글쓴이 부분은 추후에 로그인 구현 시, Session이나 쿠키에서 가져올 수 있도록 구현

## 2) 글쓰기를 담당하는 ```ArticleController``` 구현

- [x] ```/qna```로 ```GET```요청이 들어오면 ```/qna/form.html```을 보여준다
- [x] 질문 작성한 폼을 가지고 ```/qna/questions```로 ```POST```요청을 보낸다.
- [x] ```/qna/questions```에 대한 POST 요청을 처리한다
- [x] ```/``` 경로로 리다이렉트한다.

## 3) qna를 저장하는 DB와 Model 구현

- [x] Article 객체 생성
    - 글쓴이, 제목, 내용, 생성 시간 [4개!]
- [x] ArticleDatabse 데이터베이스 생성 [ArrayList로 생성]

## 4) qna 목록 출력하기

- [x] Model에 ArrayList 객체를 넘겨서 처리한다
- [x] Mustache의 부분 템플릿을 활용해서 arrayList를 순회하여 보여준다

## 5) 게시글 상세보기

- [x] 게시글 제목을 클릭했을 때, 게시글 상세 피이지에 접속하도록 한다
    - ```<a>```태그를 이용해서 구현

> 게시글 상세 페이지 접근 URL은 "/articles/{index}"
> (예를 들어 첫번째 글은 /articles/1)와 같이 구현한다.

- [x] ```Article```객체에 ```id 인스턴스 변수``` 추가
    - ```ArticleDatabase [ArrayList]```에 게시글 객체를 추가할 때, ```ArrayList.size() + 1```을 게시글 객체의 id로 사용!
- [x] ```ArticleController```에 상세 페이지 접근 추가
    - ```@GetMaapping("/{id})``` 로 매핑
- [x] 데이터 조회하기
    - ```ArticleDatabase```에서 ```index-1```에 해당하는 데이터를 조회하고 model에 전달

## 추가 구현 : 사용자 정보 수정하기
### 수정 페이지
- [x] 사용자 프로필 화면에 수정하기 버튼 추가하기
- [x] 사용자 인증을 위해 사용자 비밀번호 확인하는 기능 추가하기
  - controller에서 확인하여, model에 true/false 넘겨주어 mustache로 구현
- [x] 사용자 프로필을 수정할 수 있는 페이지 추가하기
- [x] 입력한 폼을 이용해서 사용자 정보를 수정하기
### 수정 작업
- [x] 수정페이지에서 입력한 값 **[수정하고자 하는 정보]** 를 받는다
- [x] UserController에서 입력받은 값을 UserUpdateDate 객체로 UserManagementService로 전달한다.
- [x] UserDatabase에서 UserId에 해당하는 user를 가져온 후, 유저의 비밀번호와  UserUpdateData의 비밀번호를 확인한다.
- [x] User 객체의 updateUser(UserUpdataData) 을 이용해서 업데이트한다.
  - 기존에는 유저 객체의 불변성을 유지하고 싶어서, 새로운 유저 객체를 생성하고, ArrayList에 해당 유저를 replace하려고 했다.
  - setter를 사용하는 것도 고려해보았지만, 좋은 방법같지 않았다.
  - 결과적으로 User 객체에 updateUser 메소드를 두어 내부적으로 값을 바꾸도록 하였다..
    - 이 방법도 옳은 것인지 잘 모르겠다..
- [x] ```User```객체를 새로 입력받은 값으로 업데이트 한다

## 추가 구현 : ```PUT```메소드 사용하여 유저 정보 업데이트 하기
- [ ] input 태그를 put 메소드로 바꾸기
- [ ] application.properties에 PUT 메소드 사용을 위한 설정 추가하기

## 추가 구현 : Custom Exception 만들어보기
### 문제가 된 상황 [UserManagementService 클래스]
- ```updateUser()```에서 사용자가 존재하지 않을 경우 : ```IllegalArgumentException```
- ```findUserById()```에서 사용자가 존재하지 않을 경우 : ```Controller```에서 ```ResponseStatusException```로 처리
> 비슷한 역할을 하는 예외 처리지만, 용도가 때에 따라 다르다!
- Custom Exception을 만들어보자!
```java
// Example 이런식으로!
public class UserNotFoundException extends IllegalArgumentException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
```

# 📜 URL Convention

| URL                      | 기능                             | 구현 상태 |
|:-------------------------|:-------------------------------|:-----:|
| GET /users               | 회원가입을 할 수 있는 폼을 보여준다           |  ⭕️   |
| POST /users              | 입력된 폼을 가지고, 회원 가입을 수행한다        |  ⭕️   |
| GET /users/list          | 회원가입된 유저들을 보여준다.               |  ⭕️   |
| GET /users/:userId       | useeId에 해당하는 profile을 보여준다     |  ⭕️   |
| GET /articles            | 게시물 입력하는 폼을 보여준다               |  ⭕️   |
| POST /articles           | 입력한 폼을 POST 요청으로 보낸다           |  ⭕️   |
| GET /articles/:articleId | articleId에 해당하는 게시물 상세정보를 보여준다 |  ⭕️   |
| GET /                    | 등록된 모든 qna를 보여준다               |  ⭕️   |

---

# 🤯 마주친 오류

## REDIRECT 시에 Model 넘기기?

- ```Step-2``` 글쓰기 기능 구현을 하면서 POST로 Qna 처리를 한 후, ```/```경로로 리다이렉트를 해주어야 했다.
- 기존에는 ```Model model```을 선언하여, model.setAttribute로 값을 넘겨주었다.

### 문제사항

- redirect 시에는, ResponseBody가 없기 때문에 Model을 넘기기 힘들지 않을까?!
- 따라서, 기본적인 Model을 사용하면 안된다.

### ⭕️ 해결

- 일단은 ```/```경로로 접속하게 된다면, ```ArticleDatabase```에 있는 모든 Article을 가져오도록 구현하면 된다.
- 그렇다면, ```MainController```에서 ```Get```요청이 들어올 때, ```ArticleDatabase.getAllArticles```을 통해 ```/main/index.html```에 보내주면
  도니다.

> 하지만, 이러한 방법 말고 Redirect시에는 아예 값을 넘겨줄수는 없는가?

### 기본적으로, Model속성을 적용하면 URL의 쿼리 문으로 가게 된다!

```
@RequestMapping("/redirect")
public String redirect(Model model) {
	model.addAttribute("mesg", "hello");
	return "redirect:main";
}
```

```
url : localhost:8080/redirect/main?mseg=hello
```

- 이렇게 된다면 리다이렉트 하려는 요소들이 모두 보이게 되어, 정상적인 동작이 이루어지지 않을 수 있다!

### RedirectAttributes 를 사용하자!

- @RequestMapping 메소드의 파라미터 타입으로 RedirectAttributes 객체를 지정하면 특정 속성을 리다이렉트되는 뷰 페이지에서 사용할 수 있도록 전달할 수 있다.
- 메소드가 리다이렉트되면 RedirectAttributes 객체의 내용이 사용되고, 리다이렉트되지 않는 경우 Model 객체의 내용이 사용된다.

> ### Flash Attributes

- RedirectAttributes를 사용하여 리다이렉트되는 주소로 데이터를 전달하기 위해 플래시 속성(Flash Attributes)을 사용!
    - 한 번 쓴 값을 남아있으면 안되기 때문!
- 플래시 속성은 하나의 요청에서 속성을 저장하고 다른 곳에서 사용하기 위한 방법을 제공
    - 현재 ```/``` 리다이렉션에도 쓸 수 있겠지만, 작성한 모든 qna가 모든 사용자들에게 보여야 하므로 ```/``` GetMapping이 이루어질때 동적으로 생성하는 것이 더 좋아보인다.

```java

@RequestMapping("/redirect2")
public String redirect(RedirectAttributes rttr) {
    rttr.addFlashAttribute("mesg", "hello");
    return "redirect:main";
}
```

- 위의 방식을 쓰면, main에서 model 객체를 사용하는 것 처럼 사용할 수 있다.

```html
Model 객체 : ${ mesg } <br>
Model 객체 : <%= request.getAttribute("mesg") %> <br>

-> 모두 hello
```
