package codesquad.springcafe.reply;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
public class RestReplyController {

    Logger log = org.slf4j.LoggerFactory.getLogger(RestReplyController.class);

    private final ReplyService replyService;

    @Autowired
    public RestReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/create")
    public Reply createReply(ReplyCreateDto replyCreateDto) {
        log.info(replyCreateDto.getCreatedTime());
        return replyService.save(replyCreateDto);
    }

    @DeleteMapping("/{replyId}/delete")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId, HttpSession httpSession) {
        String userId = httpSession.getAttribute("userId").toString();
        if (!replyService.isAutehnticated(userId, replyId)) {
            return ResponseEntity.badRequest().build();
        }
        replyService.delete(replyId);
        return ResponseEntity.ok().build();
    }
}
