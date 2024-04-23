package codesquad.springcafe.domain.comment;

import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment postComment(CommentPostReq commentPostReq, SimpleUserInfo author) {
        if (author == null) author = new SimpleUserInfo("guest", "익명");
        return commentRepository.add(commentPostReq, author);
    }

    public List<Comment> findByUserId(String id) {
        return commentRepository.findByUserId(id);
    }

    public void modify(int id, CommentPostReq commentPostReq) {
        commentRepository.modify(id, commentPostReq);
    }

    public void delete(int id) {
        commentRepository.delete(id);
    }

    public boolean canModify(int id, SimpleUserInfo loginUser) {
        if (loginUser == null) return false;
        return commentRepository.findById(id).getAuthorId().equals(loginUser.id());
    }

    public int getArticleId(int id) {
        return commentRepository.findById(id).getArticleId();
    }

    public List<Comment> findByArticleId(int articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<Comment> findByArticleId(int articleId, int page) {
        int COMMENTS_PER_PAGE = 15;
        int START_INDEX = COMMENTS_PER_PAGE * (page - 1);
        int LAST_INDEX = COMMENTS_PER_PAGE * (page);

        List<Comment> byArticleId = findByArticleId(articleId);
        return byArticleId.subList(START_INDEX, Math.min(LAST_INDEX, byArticleId.size()));
    }
}
