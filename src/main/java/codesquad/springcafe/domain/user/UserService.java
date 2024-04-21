package codesquad.springcafe.domain.user;

import codesquad.springcafe.exception.NotFoundException;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import codesquad.springcafe.domain.user.DTO.UserListRes;
import codesquad.springcafe.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {
    private static final Logger log = getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 가입 정보를 검증하고 , 가입에 실패한다면 실패 메시지를 담은 예외를 던짐
     * @param user 유저 가입 정보
     * @throws IllegalArgumentException 실패 사유
     */
    public void create(User user) throws IllegalArgumentException{
        // Todo. 가입 정보 검증 : 지금은 ID 중복만 처리중
        userRepository.addUser(user);

        log.debug(user.toString());
    }

    /**
     * 유저 정보를 수정하고, 성공 여부를 반환
     * @param user 새 유저 정보
     * @param password 기존 비밀번호
     * @return 수정 성공 여부
     */
    public boolean update(User user, String password){
        if(userRepository.findUserById(user.getUserId()).isCorrectPassword(password)){
            userRepository.update(user);

            log.debug("user updated : " + user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 로그인 가능하다면 로그인 된 유저의 정보를 반환 , 불가하다면 null 반환
     * @param id 유저 id
     * @param password 유저 password
     * @return 유저 정보
     */
    public SimpleUserInfo login(String id, String password) {
        User user = userRepository.findUserById(id);

        if(user != null && user.isCorrectPassword(password)) {
            return new SimpleUserInfo(id, user.getName());
        };

        return null;
    }

    /**
     * @return 회원가입 된 유저들의 정보
     */
    public List<UserListRes> userList() {
        return userRepository.findAll();
    }

    /**
     * ID 로 유저를 찾아 반환
     * @param id 유저 ID
     * @return 유저 정보
     */
    public User getUser(String id){
        User user = userRepository.findUserById(id);
        if (user == null) throw new NotFoundException("존재하지 않는 유저입니다!");
        return user;
    }

    public boolean canModify(String id , SimpleUserInfo user){
        return userRepository.findUserById(id).getUserId().equals(user.id());
    }
}
