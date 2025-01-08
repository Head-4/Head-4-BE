package head4.notify.domain.notification.entity;

import head4.notify.domain.article.entity.University;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notify {
//    @EmbeddedId
//    private NotifyId id;
//
//    @MapsId("univId")
//    @ManyToOne
//    @JoinColumn(name = "univ_id")
//    private University university;

    /**
     * 조회하는 방법
     * 1. notifyId = new Notify(univId, keyword) 를 만든다.
     * 2. notifyRepository.findById(notifyId);
     */

    @Id
    @Column(name = "notify_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "univ_id")
    private University university;

    @Column(length = 100)
    private String keyword;
}
