# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## URL Convention

| 기능           | URI                  | HTTP Method |
|--------------|----------------------|-------------|
| 회원 가입 페이지    | /user/form           | `GET`       |
| 회원 리스트 조회    | /user/list           | `GET`       |
| 회원 프로필 조회    | /user/profile        | `GET`       |
| 회원 등록        | /user/create         | `POST`      |
| 게시글 등록 페이지   | /article/form        | `GET`       |
| 게시글 등록       | /article/create      | `POST`      |
| 게시글 상세 조회    | /article/{articleId} | `GET`       |
| 회원 정보 수정     | /user/{userId}/form  | `PUT`       | 
| 회원 정보 수정 페이지 | /user/{userId}/form  | `GET`       |