package head4.notify.domain.notification.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NotifyDetail {
    private Long userId;
    private String fcmToken;
    private String title;
    private String url;
    private String keyword;
}
