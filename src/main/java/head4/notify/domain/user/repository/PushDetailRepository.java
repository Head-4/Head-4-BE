package head4.notify.domain.user.repository;

import head4.notify.domain.user.entity.PushDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushDetailRepository extends JpaRepository<PushDetail, Long> {
}
