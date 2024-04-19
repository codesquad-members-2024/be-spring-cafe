package codesquad.springcafe.repository.member;

import codesquad.springcafe.controller.member.UpdateMember;
import codesquad.springcafe.domain.member.Member;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class MemberRepositoryH2 implements MemberRepository {

    private final JdbcTemplate template;

    @Autowired
    public MemberRepositoryH2(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO MEMBER(LOGIN_ID, PASSWORD, USERNAME, EMAIL) VALUES(?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getLoginId());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getUserName());
            ps.setString(4, member.getEmail());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("pk를 생성하지 못했습니다");
        }

        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(String loginId) {
        String sql = "select MEMBER_ID, LOGIN_ID, PASSWORD, USERNAME, EMAIL from MEMBER where LOGIN_ID = ?";
        try {
            return Optional.ofNullable(template.queryForObject(sql, memberRowMapper(), loginId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select MEMBER_ID, LOGIN_ID, PASSWORD, USERNAME, EMAIL from MEMBER";
        return template.query(sql, memberRowMapper());
    }

    @Override
    public void update(String loginId, UpdateMember updateParam) {
        String sql = "update MEMBER set PASSWORD=?, USERNAME=?, EMAIL=? where LOGIN_ID=?";
        template.update(sql, updateParam.getAfterPassword(), updateParam.getUserName(), updateParam.getEmail(),
                loginId);
    }

    @Override
    public void clear() {
        String sql = "alter table if exists ARTICLE drop constraint if exists fk_created_by; truncate table MEMBER; alter table MEMBER alter column MEMBER_ID restart with 1";
        template.update(sql);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("member_id"));
            member.setLoginId(rs.getString("login_id"));
            member.setPassword(rs.getString("password"));
            member.setUserName(rs.getString("username"));
            member.setEmail(rs.getString("email"));
            return member;
        };
    }
}
