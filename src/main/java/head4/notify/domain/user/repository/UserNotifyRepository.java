package head4.notify.domain.user.repository;

import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotifyRepository extends JpaRepository<UserNotify, UserNotifyId> {
}
