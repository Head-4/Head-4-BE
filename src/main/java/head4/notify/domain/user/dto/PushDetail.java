package head4.notify.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PushDetail {
    private Long userId;

    private String fcmToken;

    private String title;

    private String url;
}
