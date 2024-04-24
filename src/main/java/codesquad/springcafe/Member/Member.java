package codesquad.springcafe.Member;


public class Member {
    private final String memberId;
    private String name;
    private String email;
    private String password;
    private Long id;

    public Member(String memberId, String name, String email, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setEmail(String email) {this.email = email;}

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {this.password = password;}

    public String getPassword(){ return password;}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}