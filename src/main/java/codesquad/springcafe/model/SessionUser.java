package codesquad.springcafe.model;

import java.util.ArrayList;
import java.util.List;

public class SessionUser {
    private final String userId;
    private final String userName;
    private final String userEmail;
    private List<Long> articleIds;

    public SessionUser(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.articleIds = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<Long> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(List<Long> articleIds) {
        this.articleIds = articleIds;
    }

    public void addArticleId(Long articleId) {
        this.articleIds.add(articleId);
    }
}