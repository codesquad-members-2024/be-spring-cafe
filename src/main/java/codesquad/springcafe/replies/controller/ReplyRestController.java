package codesquad.springcafe.replies.controller;

import codesquad.springcafe.replies.model.Reply;
import codesquad.springcafe.replies.model.dto.ReplyCreationRequest;
import codesquad.springcafe.replies.model.dto.ReplyViewDto;
import codesquad.springcafe.replies.service.ReplyService;
import codesquad.springcafe.utils.AuthValidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/replies")
public class ReplyRestController {
    private final ReplyService replyService;
    private final AuthValidateService authValidateService;

    @Autowired
    public ReplyRestController(ReplyService replyService, AuthValidateService authValidateService) {
        this.replyService = replyService;
        this.authValidateService = authValidateService;
    }

    @PostMapping
    public ResponseEntity<ReplyViewDto> createReply(HttpSession session, @PathVariable long articleId, @RequestBody ReplyCreationRequest replyCreationRequest) {
        Reply reply = replyService.createReply(articleId, replyCreationRequest);

        authValidateService.validateReplyAuth(session, reply);

        ReplyViewDto replyViewDto = new ReplyViewDto(reply, true);

        replyViewDto.setArticleId(articleId);

        return ResponseEntity.ok(replyViewDto);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Boolean> deleteReply(HttpSession session, @PathVariable long replyId) {
        Reply reply = replyService.findReplyById(replyId);

        authValidateService.validateReplyAuth(session, reply);

        boolean result = replyService.deleteReply(replyId);

        return ResponseEntity.ok(result);
    }


}
