package head4.notify.domain.user.repository;

import head4.notify.domain.notification.entity.dto.PushMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PushDetailJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void batchSave(List<PushMessage> messages) {
        String sql = "INSERT INTO push_detail (user_id, notify_id, article_id) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, messages.get(i).getUserId());
                        ps.setLong(2, messages.get(i).getNotifyId());
                        ps.setLong(3, messages.get(i).getArticleId());
                    }

                    @Override
                    public int getBatchSize() {
                        return messages.size();
                    }
                }
        );
    }
}
