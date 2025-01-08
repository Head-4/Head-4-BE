package head4.notify.domain.notification.repository;

import head4.notify.domain.notification.entity.NotifyArticle;
import head4.notify.domain.notification.entity.embedded.NotifyArticleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyArticleRepository extends JpaRepository<NotifyArticle, NotifyArticleId> {
}
