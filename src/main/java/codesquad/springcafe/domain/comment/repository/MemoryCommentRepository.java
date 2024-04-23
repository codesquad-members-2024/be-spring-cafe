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
    public Comment add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Comment comment = new Comment(nowIndex, commentPostReq.articleId(), commentPostReq.content(),
                Timestamp.valueOf(LocalDateTime.now()), simpleUserInfo.name(), simpleUserInfo.id());

        comments.put(nowIndex, comment);
        nowIndex ++;
        return comment;
    }

    @Override
    public List<Comment> findByArticleId(int id) {
        return comments.values().stream().filter(c-> c.getArticleId() == id).toList();
    }

    @Override
    public List<Comment> findByArticleId(int articleId, int page) {
        int COMMENTS_PER_PAGE = 15;
        int START_INDEX = COMMENTS_PER_PAGE * (page - 1);
        int LAST_INDEX = COMMENTS_PER_PAGE * page;

        List<Comment> byArticleId = findByArticleId(articleId);
        return byArticleId.subList(START_INDEX, Math.min(LAST_INDEX, byArticleId.size()));
    }

    @Override
    public List<Comment> findByUserId(String id) {
        return comments.values().stream().filter(c-> c.getAuthorId().equals(id)).toList();
    }

    @Override
    public void modify(int id, CommentPostReq commentPostReq) {
        Comment prev = comments.remove(id);
        SimpleUserInfo author = new SimpleUserInfo(prev.getAuthor(), prev.getAuthor());
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
