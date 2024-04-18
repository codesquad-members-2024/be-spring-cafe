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

    public void postComment(CommentPostReq commentPostReq, SimpleUserInfo author) {
        if (author == null) author = new SimpleUserInfo("guest", "익명");
        commentRepository.add(commentPostReq, author);

    }

    public List<Comment> findByUserId(String id) {
        return commentRepository.findByUserId(id);
    }

    public void modify(int id, CommentPostReq commentPostReq) {
        commentRepository.modify(id, commentPostReq);
    }

    public void delete(int id){
        commentRepository.delete(id);
    }

    public boolean canModify(int id, SimpleUserInfo loginUser) {
        if(loginUser == null) return false;
        return commentRepository.findById(id).authorId().equals(loginUser.id());
    }

    public int getArticleId(int id) {
        return commentRepository.findById(id).articleId();
    }
}
