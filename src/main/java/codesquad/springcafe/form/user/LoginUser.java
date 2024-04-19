package codesquad.springcafe.form.user;

import java.util.Set;

public class LoginUser {
    private final String nickname;
    private final Set<Long> ownArticleIds;

    public LoginUser(String nickname, Set<Long> ownArticleIds) {
        this.nickname = nickname;
        this.ownArticleIds = ownArticleIds;
    }

    public boolean hasSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean isOwnArticle(Long id) {
        return ownArticleIds.contains(id);
    }

    public String getNickname() {
        return nickname;
    }

    public Set<Long> getOwnArticleIds() {
        return ownArticleIds;
    }
}
