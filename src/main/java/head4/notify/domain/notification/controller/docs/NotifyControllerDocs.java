package head4.notify.domain.notification.controller.docs;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.notification.entity.dto.AddKeywordsRequest;
import head4.notify.exceoption.ErrorCode;
import head4.notify.security.custom.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Notify API",  description = "알림 관련 기능 API")
public interface NotifyControllerDocs {

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[온보딩, 설정] 키워드 추가", description = "사용자가 지정한 키워드들을 등록하는 API")
    @Parameter(description = "알림 설정할 키워드",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = String.class))
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "success"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> addKeywords(AddKeywordsRequest request, CustomUserDetails userDetails);
}
