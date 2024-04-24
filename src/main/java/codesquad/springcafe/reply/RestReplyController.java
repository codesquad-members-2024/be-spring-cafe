package codesquad.springcafe.reply;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
}
