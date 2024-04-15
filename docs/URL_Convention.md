### `/user`

| URL                    | 기능            |
|------------------------|---------------|
| GET /user/form         | 회원 가입 페이지     |
| POST /user             | 회원 가입         |
| GET /user/login/form   | 로그인 페이지       |
| POST /user/login       | 로그인           |
| POST /user/logout      | 로그아웃          |
| GET /user/:id/form     | 회원 정보 수정 페이지  |
| PUT /user/:id          | 회원 정보 수정      |
| GET /user/:id/delete   | 회원 탈퇴 확인 페이지  |
| DELETE /user/:id       | 회원 탈퇴         |
| GET /user/users        | 유저 리스트 페이지    |
| GET /user/:id          | 유저 프로필 페이지    |
| GET /user/:id/articles | 작성한 글 목록 페이지  |
| GET /user/:id/comments | 작성한 댓글 목록 페이지 |


### `/article`

| URL                     | 기능          |
|-------------------------|-------------|
| GET /article/form       | 글 작성 페이지    |
| POST /article           | 글 게시        |
| GET /article/:id/form   | 글 수정 페이지    |
| PUT /article/:id        | 글 수정        |
| GET /article/:id/delete | 글 삭제 확인 페이지 |
| DELETE /article/:id     | 글 삭제        |


### `/comment`

| URL                     | 기능           |
|-------------------------|--------------|
| GET /comment/form       | 댓글 작성 페이지    |
| POST /comment           | 댓글 게시        |
| GET /comment/:id/form   | 댓글 수정 페이지    |
| PUT /comment/:id        | 댓글 수정        |
| GET /comment/:id/delete | 댓글 삭제 확인 페이지 |
| DELETE /comment/:id     | 댓글 삭제        |




