package codesquad.springcafe.controller;

import codesquad.springcafe.dto.reply.ReplyInfoDTO;
import codesquad.springcafe.dto.reply.ReplyUploadDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("articles/{articleId}/replies")
    public String upload(@ModelAttribute("reply") ReplyUploadDTO replyUploadDTO, @PathVariable Long articleId, Model model) {
        ReplyInfoDTO newReply = replyService.upload(replyUploadDTO);
        model.addAttribute("reply", newReply);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("articles/{articleId}/replies/{index}")
    public String delete(@PathVariable Long articleId, @PathVariable Long index, HttpSession session) {
        ReplyInfoDTO reply = replyService.findByArticleIdAndIndex(articleId, index);
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("loggedInUser");
        if (!reply.isWriter(user.getUserId())) {
            return "/reply/delete_failed";
        }
        replyService.delete(articleId, index);
        return "redirect:/articles/{articleId}";
    }
}