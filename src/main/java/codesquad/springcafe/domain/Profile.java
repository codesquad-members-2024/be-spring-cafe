package codesquad.springcafe.domain;

public class Profile {

    private Long id;
    private Long memberId;
    private String address;
//    private String city;
//    private String selfIntroduction;

    public Profile(
            Long memberId,
            String address
//            String city,
//            String selfIntroduction
    ) {
        this.memberId = memberId;
        this.address = address;
//        this.city = city;
//        this.selfIntroduction = selfIntroduction;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getAddress() {
        return address;
    }

//    public String getCity() {
//        return city;
//    }
//
//    public String getSelfIntroduction() {
//        return selfIntroduction;
//    }

    public void setId(Long id) {
        this.id = id;
    }
}
