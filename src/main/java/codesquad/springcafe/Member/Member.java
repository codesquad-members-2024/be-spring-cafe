package codesquad.springcafe.Member;

public class Member {
    private String memberId;
    private final String name;
    private final String email;
    private final String password;

    public Member(String memberId, String name, String email, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword(){ return password;}
}
