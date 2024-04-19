package codesquad.springcafe.dto;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.model.User;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ArticleUpdateDTO {

    private final String title;
    private final String contents;
    private final List<Reply> replies;

    public ArticleUpdateDTO(String title, String contents, List<Reply> replies) {
        this.title = title;
        this.contents = contents;
        this.replies = replies;
    }

    public Article toArticle(Article original) {
        return new Article(original.getIndex(), original.getTimeStamp(), original.getWriter(), title, contents, replies);
    }
}
