package codesquad.springcafe.replies.controller;

import codesquad.springcafe.replies.model.Reply;
import codesquad.springcafe.replies.model.dto.ReplyCreationRequest;
import codesquad.springcafe.replies.model.dto.ReplyViewDto;
import codesquad.springcafe.replies.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/replies")
public class ReplyRestController {
    private final ReplyService replyService;

    @Autowired
    public ReplyRestController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public ResponseEntity<ReplyViewDto> createReply(@PathVariable long articleId, @RequestBody ReplyCreationRequest replyCreationRequest) {
        Reply reply = replyService.createReply(articleId, replyCreationRequest);

        ReplyViewDto replyViewDto = new ReplyViewDto(reply, true);

        replyViewDto.setArticleId(articleId);

        return ResponseEntity.ok(replyViewDto);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Boolean> deleteReply(@PathVariable long replyId) {
        boolean result = replyService.deleteReply(replyId);

        return ResponseEntity.ok(result);
    }


}
