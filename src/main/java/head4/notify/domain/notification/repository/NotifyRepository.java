package head4.notify.domain.notification.repository;

import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.entity.embedded.NotifyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, NotifyId> {
}
