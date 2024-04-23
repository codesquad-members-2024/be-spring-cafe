package codesquad.springcafe.controller;

import codesquad.springcafe.dto.CommentWriteDto;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.service.CommentService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}/comments")
    public Map<String, Object> processCommentForm(@PathVariable long articleId, HttpSession httpSession,
                                                  @RequestBody CommentWriteDto commentWriteDto) {
        if (commentWriteDto.isEmpty()) {
            return Map.of("ERROR", "EMPTY_COMMENT_CONTENT");
        }
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        String userId = sessionUser.getUserId();
        Comment comment = commentWriteDto.createComment(articleId, userId);
        commentService.addComment(comment);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> addCommentMap = new HashMap<>();
        addCommentMap.put("userId", comment.getUserId());
        addCommentMap.put("content", comment.getContent());
        addCommentMap.put("creationTime", comment.getFormattedCreationTime());
        response.put("ADD_COMMENT", addCommentMap);
        return response;
    }
}
