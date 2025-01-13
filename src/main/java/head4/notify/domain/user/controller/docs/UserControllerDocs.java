package head4.notify.domain.user.controller.docs;

import head4.notify.customResponse.BaseResponse;
import head4.notify.exceoption.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User API",  description = "사용자 관련 기능 API")
public interface UserControllerDocs {
    @Operation(summary = "대학교 저장", description = "사용자가 선택한 대학 정보를 저장하는 API")
    @Parameter(name = "name", description = "대학교 이름", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class))),
            @ApiResponse(responseCode = "40402", description = "대학교 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> patchUniv(String name);
}
