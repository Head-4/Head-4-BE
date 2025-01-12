package head4.notify.domain.notification.entity.dto;

import lombok.Getter;

@Getter
public class NotifyCreateRequest {
    private Long userId;

    private String keyword;
}
