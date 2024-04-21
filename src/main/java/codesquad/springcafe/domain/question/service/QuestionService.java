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
        Question question = questionRequest.toQuestion(user.getId());
        Question saved = questionRepository.save(question);

        return saved.getId();
    }

    // 질문 목록 조회
    public QuestionListResponse getQuestions() {
        List<QuestionResponse> questions = questionRepository.findAll().stream()
                .map(q -> {
                    User writer = userRepository.findById(q.getUserId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
                    return new QuestionResponse(q.getId(), writer.getName(), writer.getLoginId(), q.getTitle(),
                            q.getContent(), DateUtils.convertCreatedAt(q.getCreatedAt()), q.getViewCnt());
                })
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

        // 작성자 조회
        User writer = userRepository.findById(question.getUserId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        return new QuestionResponse(question.getId(), writer.getName(), writer.getLoginId(), question.getTitle(), question.getContent(),
                DateUtils.convertCreatedAt(question.getCreatedAt()), question.getViewCnt());
    }

    // 질문 수정
    public void editQuestion(Long userId, Long questionId, QuestionRequest questionUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));

        if (!question.getUserId().equals(user.getId())) {
            throw new IllegalStateException("권한이 없습니다.");
        }

        Question updateQuestion = question.update(questionUpdateRequest.getTitle(), questionUpdateRequest.getContent());
        questionRepository.update(questionId, updateQuestion);
    }
}
