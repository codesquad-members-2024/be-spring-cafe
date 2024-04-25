package codesquad.springcafe.domain.comment.service;

import codesquad.springcafe.domain.comment.data.CommentListResponse;
import codesquad.springcafe.domain.comment.data.CommentRequest;
import codesquad.springcafe.domain.comment.data.CommentResponse;
import codesquad.springcafe.domain.comment.model.Comment;
import codesquad.springcafe.domain.comment.model.CommentRepository;
import codesquad.springcafe.domain.question.model.Question;
import codesquad.springcafe.domain.question.model.QuestionRepository;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import codesquad.springcafe.global.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    // 댓글 등록
    public Long createComment(Long userId, CommentRequest commentCreateRequest) {
        // 사용자 인증
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자압니다."));

        // 댓글 달 게시글 조회
        Question question = questionRepository.findById(commentCreateRequest.getQuestionId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        // 게시글 작성자가 탈퇴한 사용자인 경우 댓글 등록 불가
        if (question.getUser().getDeleted()) {
            throw new IllegalStateException("작성자가 탈퇴한 게시글에는 댓글을 달 수 없습니다.");
        }

        // 댓글 등록
        Comment saved = commentRepository.save(commentCreateRequest.toComment(user, question));
        return saved.getId();
    }

    // 댓글 조회
    public CommentListResponse getComments(Long userId, Long questionId) {
        // 사용자 인증
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        // 댓글 목록 조회
        List<CommentResponse> comments = commentRepository.findByQuestionId(questionId)
                .stream().map(c -> {
                    boolean isUserWithdrawn = c.getUser().getDeleted();
                    return new CommentResponse(c.getId(), isUserWithdrawn ? "탈퇴한 사용자" : c.getUser().getLoginId(),
                            isUserWithdrawn ? "탈퇴한 사용자" : c.getUser().getName(), c.getContent(),
                            DateUtils.convertLocalDateTimeToString(c.getModifiedAt()),
                            c.getModified(), c.getUser().getId().equals(user.getId()), isUserWithdrawn);
                }).toList();

        return new CommentListResponse(comments);
    }
}
