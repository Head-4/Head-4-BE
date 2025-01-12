package head4.notify.domain.user.entity;

import head4.notify.domain.user.entity.embedded.UserNotifyId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import static lombok.AccessLevel.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class UserNotify implements Persistable<UserNotifyId> {
    @EmbeddedId
    private UserNotifyId userNotifyId;

    @Override
    public UserNotifyId getId() {
        return userNotifyId;
    }

    // GeneratedValue를 설정할 수 없는 엔티티일 경우
    // jpa에서 save 메서드가 실행되면 해당 객체가 존재하는지 select문을 실행함
    // isNew를 오버라이딩하여 구현하면 해당 작업으로 중복객체를 확인하여 select문 실행 안함
    @Override
    public boolean isNew() {
        return getUserNotifyId() == null;
    }
}
