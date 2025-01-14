package head4.notify.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PushLogPageRes {
    @Schema(description = "사용자가 받은 알림 목록 마지막 요소의 Id")
    private Long curosr;

    @Schema(description = "다음 페이지가 존재하는지 여부")
    private Boolean hasNext;

    @Schema(description = "사용자가 받은 알림 목록")
    private List<PushLog> pushLogs = new ArrayList<>();
}
