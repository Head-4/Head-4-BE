package head4.notify.domain.user.entity;

import head4.notify.customResponse.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PushDetail{
    @Id
    @Column(name = "push_detail_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long userId;

    private Long notifyId;

    private Long articleId;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    public PushDetail(Long userId, Long notifyId, Long articleId) {
        this.userId = userId;
        this.notifyId = notifyId;
        this.articleId = articleId;
    }
}
