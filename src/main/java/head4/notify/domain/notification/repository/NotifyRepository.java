package head4.notify.domain.notification.repository;

import head4.notify.domain.notification.entity.Notify;
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

    boolean existsByUnivIdAndKeyword(@Param("univId") Long univId,
                                     @Param("keyword") String keyword);

    Optional<Notify> findNotifyByUnivIdAndKeyword(@Param("univId") Long univId,
                                                  @Param("keyword") String keyword);

    // 여기서 사용자까지 조회하면 되지 않을까
    // 공지 여러개에 동일 키워드가 있으면 알림Id가 중복으로 들어감
    @Query("select n.id " +
            "from Notify n " +
            "join Article a on a.univId = n.univId " +
            "where a.id in (:articleIds) " +
            "and a.title like concat('%', n.keyword, '%') ")
    Set<Long> findMatchingNotify(@Param("articleIds") List<Long> articleIds);
}
