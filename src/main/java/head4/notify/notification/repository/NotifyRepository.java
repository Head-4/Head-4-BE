package head4.notify.notification.repository;

import head4.notify.notification.entity.Notify;
import head4.notify.notification.entity.embedded.NotifyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, NotifyId> {
}
