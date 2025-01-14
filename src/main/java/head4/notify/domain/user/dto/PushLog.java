package head4.notify.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PushLog {
    @Schema(description = "사용자가 받은 알림 Id")
    private Long pushId;

    @Schema(description = "알림 생성 날짜")
    private LocalDateTime createdDate;

    @Schema(description = "알림 키워드")
    private String keyword;

    @Schema(description = "알림 공지 제목")
    private String title;

    @Schema(description = "알림 공지 URL")
    private String url;
}
