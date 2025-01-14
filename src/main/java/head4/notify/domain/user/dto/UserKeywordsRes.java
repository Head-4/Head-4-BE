package head4.notify.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserKeywordsRes {
    @Schema(description = "사용자가 추가한 알림 Id")
    private Long notifyId;

    @Schema(description = "사용자가 추가한 키워드")
    private String keyword;
}
