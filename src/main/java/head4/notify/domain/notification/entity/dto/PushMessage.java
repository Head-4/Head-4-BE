package head4.notify.domain.notification.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PushMessage {
    private Long userId;
    private Long notifyId;
    private Long articleId;
    private String fcmToken;
    private String title;
    private String url;
    private String keyword;
}
