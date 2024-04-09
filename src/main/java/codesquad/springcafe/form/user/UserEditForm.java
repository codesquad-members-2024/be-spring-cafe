package codesquad.springcafe.form.user;

public record UserEditForm(String nickname, String currentPassword, String newPassword) {
}
