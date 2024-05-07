# ☕️ Welcome to fu-Cafe

푸카페가 궁금하다면 푸바오를 클릭하세요!

<br><br>

<a href="http://13.125.241.142:8080" target="_blank">
  <img src="https://github.com/zzawang/be-spring-cafe/assets/103445254/ff2b0d11-0f88-4114-82b9-c8410e77ef05" alt="fubao" width="200" height="200">
</a>


<br><br>

## 🌱 URL convention

| URL                                              | 기능                                                                                                       |
|--------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| GET /, /home                                     | **게시글 목록 페이지를 제공한다.**                                                                                    |
| GET /article/write                               | **게시글 작성 페이지를 제공한다.**                                                                                    |
| POST /article/write                              | **작성된 게시글을 저장 후 게시글 목록 페이지로 리다이렉트한다.**                                                                   |
| GET /article/{articleId}                         | **게시글 상세 페이지를 제공한다.**                                                                                    |
| GET /article/update/{articleId}                  | **게시글 수정 페이지를 제공한다.**                                                                                    |
| PUT /article/update/{articleId}                  | **게시글을 수정한 후 해당 게시글 페이지로 리다이렉트한다.**                                                                      |
| DELETE /article/update/{articleId}               | **게시글을 삭제한 후 게시글 목록 페이지로 리다이렉트한다.**                                                                      |
| POST /article/{articleId}/comments               | **댓글을 작성한 후 해당 게시글 페이지로 리다이렉트한다.**                                                                       |
| DELETE /article/{articleId}/comments/{commentId} | **댓글을 삭제한 후 해당 게시글 페이지로 리다이렉트한다.**                                                                       |
| GET /article/invalid-modify                      | **다른 사용자의 게시글과 댓글은 수정할 수 없다는 오류 페이지를 띄운다.**                                                              |
| GET /article/invalid-delete                      | **댓글이 없는 경우만 삭제 가능하다는 오류 페이지를 띄운다.**                                                                     |
| GET /users/join                                  | **회원가입 페이지를 제공한다.**                                                                                      |
| POST /users/join                                 | **회원가입 처리 후 유저 리스트 페이지로 리다이렉트한다.**                                                                       |
| GET /users/join/success                          | **회원가입 성공 페이지를 제공한다.**                                                                                   |
| GET /users/login                                 | **로그인 페이지를 제공한다.**                                                                                       |
| POST /users/login                                | **로그인을 한 후 리다이렉트 해야하는 url이 있다면 그 url로 돌아가고, 없다면 게시글 목록 페이지로 리다이렉트한다.**                                   |
| GET /users/logout                                | **로그아웃을 한 후 게시글 목록 페이지로 리다이렉트한다.**                                                                       |
| GET /users/list                                  | **회원가입한 사용자 목록을 보여주는 페이지를 제공한다.**                                                                        |
| GET /users/profile/{userId}                      | **userId에 해당하는 사용자의 프로필 페이지를 제공한다.**                                                                     |
| GET /users/match-pw/{userId}                     | **비밀번호 확인 페이지를 제공한다.**                                                                                   |
| POST /users/match-pw/{userId}                    | **비밀번호 일치 여부에 따라 valid값을 설정하여 클라이언트에 전달한다.<br> 클라이언트는 valid값이 true면 회원정보 수정 페이지로 이동하고 false면 알림창을 띄운다.** |
| GET /users/update/{userId}                       | **회원정보 수정 페이지를 제공한다.**                                                                                   |
| PUT /users/update/{userId}                       | **회원정보를 수정한 후 유저 리스트 페이지로 리다이렉트한다.**                                                                     |
| GET /users/invalid-modify                        | **다른 사람의 회원정보를 수정할 수 없다는 오류 페이지를 띄운다.**                                                                  |

<br><br>