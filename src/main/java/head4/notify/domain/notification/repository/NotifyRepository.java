package head4.notify.domain.notification.repository;

import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.entity.dto.NotifyDetail;
import head4.notify.domain.notification.entity.dto.NotifyIdProjection;
import head4.notify.domain.notification.entity.embedded.NotifyArticleId;
import head4.notify.domain.notification.entity.embedded.NotifyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NotifyRepository extends JpaRepository<Notify, NotifyId> {

    boolean existsByUnivIdAndKeyword(@Param("univId") int univId,
                                     @Param("keyword") String keyword);

    Optional<Notify> findNotifyByUnivIdAndKeyword(@Param("univId") int univId,
                                                  @Param("keyword") String keyword);

    // 크롤링한 공지 식별자(articleIds)
    // 해당 대학교 사용자가 등록 한 키워드가 포함된 공지와 사용자
    @Query("select new head4.notify.domain.notification.entity.dto.NotifyDetail(" +
            "u.id, u.fcmToken, a.title, a.url, n.keyword) " +
            "from Notify n " +
            "left join Article a on a.univId = n.univId " +
            "left join UserNotify un on un.userNotifyId.notifyId = n.id " +
            "left join User u on un.userNotifyId.userId = u.id " +
            "where a.id in (:articleIds) " +
            "and a.title like concat('%', n.keyword, '%') ")
    List<NotifyDetail> findMatchingNotify(@Param("articleIds") List<Long> articleIds);
}
