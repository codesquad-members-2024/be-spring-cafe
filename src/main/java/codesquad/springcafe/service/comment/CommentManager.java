package codesquad.springcafe.service.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.repository.comment.CommentRepository;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentManager implements CommentService {

    private final CommentRepository repository;

    @Autowired
    public CommentManager(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment publish(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public Comment findComment(long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 댓글을 찾을 수 없습니다. 댓글 아이디: " + commentId));
    }

    @Override
    public List<Comment> findAllComment(long articleId) {
        return repository.findAllByArticleId(articleId);
    }

    @Override
    public Page<Comment> findAllComment(long articleId, PageRequest pageRequest) {
        Page<Comment> page = repository.findAllByArticleId(articleId, pageRequest);

        /* 요청한 페이지 번호가 전체 페이지 개수보다 많으면 ResourceNotFoundException 예외 */
        if (pageRequest.getPageNumber() >= page.getTotalPages()) {
            throw new ResourceNotFoundException("전체 페이지 개수를 넘는 페이지를 요청할 수 없습니다.");
        }

        return page;
    }

    @Override
    public void editComment(String loginId, CommentUpdateForm updateParam) {
        /* 작성자 검증 */
        validateAuthor(loginId, updateParam.getAuthor());

        repository.update(updateParam);
    }

    @Override
    public void unpublish(long id) {
        repository.softDelete(id);
    }

    @Override
    public void validateAuthor(String loginId, String author) {
        if (!loginId.equals(author)) {
            throw new UnauthorizedException("로그인한 아이디로 작성한 댓글이 아닙니다. 로그인 아이디: " + loginId);
        }
    }
}
