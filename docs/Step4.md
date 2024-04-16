### 작성한 글 , 댓글 페이지 , 포인트 ✔️

- 유저 프로필 페이지에서 작성한 글 , 댓글 페이지 이동 가능
- 작성한 글 페이지에서는 유저의 총 조회수를 확인 가능
- 해당 페이지에서 글, 댓글 수정 삭제 가능

### 게시글 권한

- 목록 조회 : 모두
- 상세 조회 : 로그인 한 유저
- 수정 , 삭제 : 작성자 , `관리자`

### 게시글 수정
- 작성자만이 제목, 내용을 수정 가능
- 수정시 작성 일시가 업데이트됨
  - (수정됨) 표시와 고민

### 댓글 기능

- 게시글과 동일
- 게시글 상세 페이지에서 조회 가능

### 관리자 페이지

- 모든 글, 댓글을 삭제 가능


### 삭제 / 회원 탈퇴

- 게시글이나 회원이 삭제되면 하위의 댓글이나 게시글이 함께 삭제되도록 설정

```sql
alter table ARTICLE drop constraint FK_AUTHORID;
-- article 테이블에 외래 키(FK) 및 ON DELETE CASCADE 설정
ALTER TABLE article ADD CONSTRAINT fk_authorId FOREIGN KEY (authorId, AUTHOR) REFERENCES users (userId, name) ON DELETE CASCADE;


alter table COMMENT drop constraint fk_articleId;
-- comment 테이블에 외래 키(FK) 및 ON DELETE CASCADE 설정
ALTER TABLE comment ADD CONSTRAINT fk_articleId FOREIGN KEY (articleId) REFERENCES article (id) ON DELETE CASCADE;
ALTER TABLE comment ADD CONSTRAINT fk_authorId_c FOREIGN KEY (authorId, AUTHOR) REFERENCES users (userId, NAME) ON DELETE CASCADE;
```
