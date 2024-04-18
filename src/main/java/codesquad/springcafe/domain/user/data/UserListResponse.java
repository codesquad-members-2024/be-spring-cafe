package codesquad.springcafe.domain.user.data;

import java.util.List;

/**
 * 각 유저 정보 데이터의 목록과 총 유저 수를 저장합니다
 */
public class UserListResponse {
    private final Integer totalUserCnt;
    private final List<UserResponse> userList;

    public UserListResponse(List<UserResponse> userList) {
        this.totalUserCnt = userList.size();
        this.userList = userList;
    }

    public Integer getTotalUserCnt() {
        return totalUserCnt;
    }

    public List<UserResponse> getUserList() {
        return userList;
    }
}
