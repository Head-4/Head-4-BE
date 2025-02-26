package head4.notify.domain.user.repository;

import head4.notify.domain.article.dto.ArticleInfo;
import head4.notify.domain.user.dto.PushLog;
import head4.notify.domain.user.entity.PushDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PushDetailRepository extends JpaRepository<PushDetail, Long> {
    @Query(value = "select new head4.notify.domain.user.dto.PushLog(" +
            "pd.id, pd.createdDate, n.keyword, a.title, a.url) from PushDetail pd " +
            "left join Notify n on pd.notifyId = n.id " +
            "left join Article a on pd.articleId = a.id " +
            "where pd.userId = :userId",
    countQuery = "select count(pd) from PushDetail pd where pd.userId = :userId")
    Page<PushLog> getPushDetailPage(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select new head4.notify.domain.user.dto.PushLog(" +
            "pd.id, pd.createdDate, n.keyword, a.title, a.url) from PushDetail pd " +
            "left join Notify n on pd.notifyId = n.id " +
            "left join Article a on pd.articleId = a.id " +
            "where pd.userId = :userId and pd.id < :cursor",
    countQuery = "select count(pd) from PushDetail pd " +
            "where pd.userId = :userId and pd.id < :cursor")
    Page<PushLog> getPushDetailPage(@Param("cursor") Long cursor, @Param("userId") Long userId, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from PushDetail pd " +
            "where pd.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
