package head4.notify.domain.notification.entity;

import head4.notify.domain.article.entity.University;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notify {
    /**
     * 조회하는 방법
     * 1. notifyId = new Notify(univId, keyword) 를 만든다.
     * 2. notifyRepository.findById(notifyId);
     */

    @Id
    @Column(name = "notify_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int univId;

    @Column(length = 100)
    private String keyword;

    public Notify(int univId, String keyword) {
        this.univId = univId;
        this.keyword = keyword;
    }
}
