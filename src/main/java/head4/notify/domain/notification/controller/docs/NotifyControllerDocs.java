package head4.notify.domain.notification.controller.docs;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.notification.entity.dto.AddKeywordsRequest;
import head4.notify.exceoption.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Notify API",  description = "알림 관련 기능 API")
public interface NotifyControllerDocs {
    @Operation(summary = "[온보딩, 설정] 키워드 추가", description = "사용자가 지정한 키워드들을 등록하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> addKeywords(AddKeywordsRequest request);
}