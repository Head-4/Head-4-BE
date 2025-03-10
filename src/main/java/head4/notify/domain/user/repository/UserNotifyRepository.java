package head4.notify.domain.user.repository;

import head4.notify.domain.user.dto.UserKeywordsRes;
import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserNotifyRepository extends JpaRepository<UserNotify, UserNotifyId> {

    @Query("select un from UserNotify un " +
            "join User u on un.userNotifyId.userId = u.id " +
            "join Notify n on un.userNotifyId.notifyId = n.id " +
            "where un.userNotifyId.notifyId in (:notifyIds)")
    List<String> find(@Param("notifyIds") List<Long> notifyIds);

    @Query("select new head4.notify.domain.user.dto.UserKeywordsRes(" +
            "n.id, n.keyword) from UserNotify un " +
            "left join Notify n on un.userNotifyId.notifyId = n.id " +
            "where un.userNotifyId.userId = :userId")
    List<UserKeywordsRes> findUserKeywords(@Param("userId") Long userId);

    @Query("select n.keyword from UserNotify un " +
            "left join Notify n on un.userNotifyId.notifyId = n.id " +
            "where un.userNotifyId.userId = :userId")
    List<String> findKeywords(@Param("userId") Long userId);

    @Query("select un from UserNotify un " +
            "where un.userNotifyId.userId = :userId " +
            "and un.userNotifyId.notifyId = :notifyId")
    Optional<UserNotify> findByNotifyIdAndUserId(@Param("userId") Long userId,
                                                 @Param("notifyId") Long notifyId);


    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from UserNotify un " +
            "where un.userNotifyId.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
