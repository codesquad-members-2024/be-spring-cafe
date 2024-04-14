package codesquad.springcafe.comment.repository;

import codesquad.springcafe.comment.DTO.Comment;
import codesquad.springcafe.comment.DTO.CommentPostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;

import java.util.List;

public interface CommentRepository {


    void add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException;

    List<Comment> findByArticleId(int id);

    List<Comment> findByUserId(int id);
}

