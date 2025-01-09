package head4.notify.domain.user.repository;

import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserNotifyRepository extends JpaRepository<UserNotify, UserNotifyId> {

    @Query("select un from UserNotify un " +
            "join User u on un.userNotifyId.userId = u.id " +
            "join Notify n on un.userNotifyId.notifyId = n.id " +
            "where un.userNotifyId.notifyId in (:notifyIds)")
    List<String> find(@Param("notifyIds") List<Long> notifyIds);
}
