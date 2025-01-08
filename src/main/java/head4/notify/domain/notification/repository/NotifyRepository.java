package head4.notify.domain.notification.repository;

import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.entity.embedded.NotifyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotifyRepository extends JpaRepository<Notify, NotifyId> {

    boolean existsByUnivIdAndKeyword(@Param("univId") Long univId,
                                     @Param("keyword") String keyword);
}
