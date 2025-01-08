package head4.notify.domain.user.entity;

import head4.notify.domain.user.entity.embedded.UserNotifyId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class UserNotify {
    @EmbeddedId
    private UserNotifyId userNotifyId;
}
