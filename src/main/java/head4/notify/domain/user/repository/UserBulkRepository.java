package head4.notify.domain.user.repository;

import head4.notify.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAllJdbc(List<User> users) {
        String sql = "INSERT INTO user (email, role_type) " +
                "VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        User user = users.get(i);
                        ps.setString(1, user.getEmail());
                        ps.setString(2, user.getRoleType().toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return users.size();
                    }
                }
        );
    }
}
