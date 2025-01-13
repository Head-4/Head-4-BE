package head4.notify.domain.notification.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AddKeywordsRequest {
    @Schema(name = "알림 등록할 키워드들")
    private List<String> keywords = new ArrayList<>();
}
