package codesquad.springcafe.domain.user.data;

import java.util.List;

public class UserListData {
    private final Integer totalUserCnt;
    private final List<UserData> userList;

    public UserListData(List<UserData> userList) {
        this.totalUserCnt = userList.size();
        this.userList = userList;
    }

    public Integer getTotalUserCnt() {
        return totalUserCnt;
    }

    public List<UserData> getUserList() {
        return userList;
    }
}
