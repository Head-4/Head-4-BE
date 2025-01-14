package head4.notify.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PushLog {
    private Long pushId;

    private LocalDateTime createdDate;

    private String keyword;

    private String title;

    private String url;
}
