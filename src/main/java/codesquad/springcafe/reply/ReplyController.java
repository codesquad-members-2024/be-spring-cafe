package codesquad.springcafe.reply;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/create")
    public String createReply(ReplyCreateDto replyCreateDto) {
        replyService.save(replyCreateDto);
        return "redirect:/article/" + replyCreateDto.getArticleId();
    }

    @DeleteMapping("/{replyId}/delete")
    public String deleteReply(@PathVariable Long replyId, HttpSession httpSession) {
        String userId = httpSession.getAttribute("userId").toString();
        if (!replyService.isAutehnticated(userId, replyId)) {
            return "redirect:/";
        }
        replyService.delete(replyId);
        return "redirect:/";
    }
}
