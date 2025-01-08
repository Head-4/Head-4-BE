package head4.notify.domain.notification.entity;

import head4.notify.domain.notification.entity.embedded.NotifyArticleId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class NotifyArticle {
    @EmbeddedId
    private NotifyArticleId notifyArticleId;
}
