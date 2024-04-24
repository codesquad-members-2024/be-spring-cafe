package codesquad.springcafe.db.user.interfaces;

public interface DuplicationCheck {
    public boolean existsByUserId(String userId);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String nickname);
}
