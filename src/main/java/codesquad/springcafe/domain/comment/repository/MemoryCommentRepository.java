package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.annotation.TestRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TestRepository
public class MemoryCommentRepository implements CommentRepository{
    Map<Integer , Comment> comments = new ConcurrentHashMap<>();

    private int nowIndex = 1;

    @Override
    public void add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        comments.put(nowIndex, new Comment(nowIndex, commentPostReq.articleId() , commentPostReq.content(),
                Timestamp.valueOf(LocalDateTime.now()), simpleUserInfo.name(), simpleUserInfo.id()));

        nowIndex ++;
    }

    @Override
    public List<Comment> findByArticleId(int id) {
        return comments.values().stream().filter(c-> c.articleId() == id).toList();
    }

    @Override
    public List<Comment> findByUserId(String id) {
        return comments.values().stream().filter(c-> c.authorId().equals(id)).toList();
    }

    @Override
    public void modify(int id, CommentPostReq commentPostReq) {
        Comment prev = comments.remove(id);
        SimpleUserInfo author = new SimpleUserInfo(prev.authorId(), prev.author());
        add(commentPostReq, author);
    }

    @Override
    public void delete(int id) {
        comments.remove(id);
    }

    @Override
    public Comment findById(int id) {
        return comments.get(id);
    }
}
