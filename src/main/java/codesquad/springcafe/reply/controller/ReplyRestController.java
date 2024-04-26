package codesquad.springcafe.reply.controller;

import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ReplyCreationRequest;
import codesquad.springcafe.articles.model.dto.ReplyViewDto;
import codesquad.springcafe.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/replies")
public class ReplyRestController {
    private final ArticleService articleService;

    @Autowired
    public ReplyRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ReplyViewDto> createReply(@PathVariable long articleId, @RequestBody ReplyCreationRequest replyCreationRequest) {
        Reply reply = articleService.createReply(articleId, replyCreationRequest);

        ReplyViewDto replyViewDto = new ReplyViewDto(reply, true);

        replyViewDto.setArticleId(articleId);

        return ResponseEntity.ok(replyViewDto);
    }

    @DeleteMapping("/delete/{replyId}")
    public ResponseEntity<Boolean> deleteReply(@PathVariable long replyId) {
        boolean result = articleService.deleteReply(replyId);

        return ResponseEntity.ok(result);
    }


}
