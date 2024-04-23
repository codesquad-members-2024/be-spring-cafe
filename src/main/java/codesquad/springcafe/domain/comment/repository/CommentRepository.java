package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;

import java.util.List;

public interface CommentRepository {


    Comment add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException;

    List<Comment> findByArticleId(int id);

    List<Comment> findByArticleId(int articleId, int page);

    List<Comment> findByUserId(String id);
    void modify(int id, CommentPostReq commentPostReq);
    void delete(int id);

    Comment findById(int id);
}

