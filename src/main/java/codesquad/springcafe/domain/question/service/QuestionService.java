package codesquad.springcafe.domain.question.service;

import codesquad.springcafe.domain.question.data.QuestionListResponse;
import codesquad.springcafe.domain.question.data.QuestionRequest;
import codesquad.springcafe.domain.question.data.QuestionResponse;
import codesquad.springcafe.domain.question.model.Question;
import codesquad.springcafe.domain.question.model.QuestionRepository;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import codesquad.springcafe.global.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * QuestionRepository와 통신하며 질문 게시글 관련 비즈니스 로직을 구현하는 클래스
 */
@Service
public class QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    // 질문 등록
    public Long postQuestion(Long userId, QuestionRequest questionRequest) {
        // 사용자 인증
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 질문 게시글 등록
        Question question = questionRequest.toQuestion(user);
        Question saved = questionRepository.save(question);

        return saved.getId();
    }

    // 질문 목록 조회
    public QuestionListResponse getQuestions(Object userId) {
        List<QuestionResponse> questions = questionRepository.findAll().stream()
                .map(q -> new QuestionResponse(q.getId(), q.getUser().getDeleted() ? "탈퇴한 사용자" : q.getUser().getName(),
                        q.getUser().getDeleted() ? "탈퇴한 사용자" : q.getUser().getLoginId(), q.getTitle(),
                        q.getContent(), DateUtils.convertLocalDateTimeToString(q.getCreatedAt()), q.getViewCnt(),
                        q.getModified(), q.getUser().getId().equals(userId), q.getUser().getDeleted()))
                .toList();

        return new QuestionListResponse(questions);
    }

    // 질문 상세 조회
    public QuestionResponse getQuestion(Long userId, Long questionId) {
        // 사용자 인증
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 질문 게시글 조회
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));

        // 게시글 조회 수 up
        question.viewCntUp();
        questionRepository.viewCntUp(questionId, question);

        return new QuestionResponse(question.getId(), question.getUser().getDeleted() ? "탈퇴한 사용자" : question.getUser().getName(),
                question.getUser().getDeleted() ? "탈퇴한 사용자" : question.getUser().getLoginId(), question.getTitle(),
                question.getContent(), DateUtils.convertLocalDateTimeToString(question.getCreatedAt()), question.getViewCnt(),
                question.getModified(), question.getUser().getId().equals(userId), question.getUser().getDeleted());
    }

    // 수정할 게시글 조회
    public QuestionResponse getEditQuestion(Long userId, Long questionId) {
        // 사용자 인증
        User user = userRepository.findById(userId, false).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 질문 게시글 조회
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));

        if (!question.getUser().getId().equals(userId)) {
            throw new IllegalStateException("다른 사람의 글을 수정할 수 없습니다.");
        }

        return new QuestionResponse(questionId, user.getName(), user.getLoginId(), question.getTitle(), question.getContent(),
                DateUtils.convertLocalDateTimeToString(question.getCreatedAt()), question.getViewCnt(), question.getModified(), true, user.getDeleted());
    }

    // 질문 수정
    public void editQuestion(Long userId, Long questionId, QuestionRequest questionUpdateRequest) {
        User user = userRepository.findById(userId, false).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));

        if (!question.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("다른 사람의 글을 수정할 수 없습니다.");
        }

        Question updateQuestion = question.update(questionUpdateRequest.getTitle(), questionUpdateRequest.getContent());
        questionRepository.update(questionId, updateQuestion);
    }

    // 삭제할 게시글 조회
    public Long getDeleteQuestion(Long userId, Long questionId) {
        // 사용자 인증
        userRepository.findById(userId, false).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 질문 게시글 조회
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));

        if (!question.getUser().getId().equals(userId)) {
            throw new IllegalStateException("다른 사람의 글을 삭제할 수 없습니다.");
        }

        return question.getId();
    }

    // 질문 삭제
    public void deleteQuestion(Long userId, Long questionId) {
        User user = userRepository.findById(userId, false).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));

        if (!question.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("다른 사람의 글을 삭제할 수 없습니다.");
        }

        Question deleteQuestion = question.delete();
        questionRepository.softDeleteById(questionId, deleteQuestion);
    }
}
