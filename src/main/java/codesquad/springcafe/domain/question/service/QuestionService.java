package codesquad.springcafe.domain.question.service;

import codesquad.springcafe.domain.question.data.QuestionListResponse;
import codesquad.springcafe.domain.question.data.QuestionPostRequest;
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
    public void postQuestion(Long userId, QuestionPostRequest questionPostRequest) {
        // 사용자 인증
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 질문 게시글 등록
        Question question = questionPostRequest.toQuestion(user);
        questionRepository.save(question);
    }

    // 질문 목록 조회
    public QuestionListResponse getQuestions() {
        List<QuestionResponse> questions = questionRepository.findAll().stream()
                .map(q -> new QuestionResponse(q.getId(), q.getUser().getLoginId(), q.getTitle(),
                        q.getContent(), DateUtils.convertCreatedAt(q.getCreatedAt()), q.getViewCnt()))
                .toList();

        return new QuestionListResponse(questions);
    }

    // 질문 상세 조회
    public QuestionResponse getQuestion(Object userId, Long questionId) {
        // 세션에 userId 값이 없으면 권한 없음 예외
        if (userId == null) {
            throw new IllegalStateException("해당 요청에 대한 권한이 없습니다.");
        }
        // 사용자 인증
        userRepository.findById((Long) userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 질문 게시글 조회
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문 게시글입니다."));
        return new QuestionResponse(question.getId(), question.getUser().getLoginId(), question.getTitle(), question.getContent(),
                DateUtils.convertCreatedAt(question.getCreatedAt()), question.getViewCnt());
    }
}
