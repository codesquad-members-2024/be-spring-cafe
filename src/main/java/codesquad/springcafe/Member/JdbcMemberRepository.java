package codesquad.springcafe.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            String memberId = rs.getString("memberId");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password;
            try {
                password = rs.getString("password");
            } catch (SQLException e) {
                password = "";
            }
            Member member = new Member(memberId, name, email, password);
            member.setId(rs.getLong("id"));
            return member;
        }
    }



    @Override
    public void addMember(Member member) {
        String sql = "INSERT INTO members(memberId, password, name, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getMemberId(), member.getPassword(), member.getName(), member.getEmail());
    }

    @Override
    public Optional<Member> findById(String memberId) {
        String sql = "SELECT id, memberId, name, password, email FROM members WHERE memberId = ?";
        List<Member> members = jdbcTemplate.query(sql, new MemberRowMapper(), memberId);
        return members.stream().findFirst();
    }

    @Override
    public List<Member> getAllMembers() {
        String sql = "SELECT id, memberId, name, email FROM members";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    @Override
    public void updateMemberInfo(Member member) {
        String sql = "UPDATE members SET password = ?, name = ?, email = ? WHERE memberId = ?";
        jdbcTemplate.update(sql, member.getPassword(), member.getName(), member.getEmail(), member.getMemberId());
    }
    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM members");
    }
}
