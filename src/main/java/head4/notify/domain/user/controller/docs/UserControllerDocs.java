package head4.notify.domain.user.controller.docs;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.user.dto.PushLogPageRes;
import head4.notify.domain.user.dto.UserKeywordsRes;
import head4.notify.exceoption.ErrorCode;
import head4.notify.security.custom.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "User API",  description = "사용자 관련 기능 API")
public interface UserControllerDocs {
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[사용자] 이메일 조회", description = "사용자 이메일 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "user@email.com"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> email(CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[사용자] 대학교 이름", description = "사용자 대학교 이름을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "상명대학교 천안캠퍼스"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class))),
            @ApiResponse(responseCode = "40402", description = "대학교 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> university(CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[사용자] 알림 허용 여부", description = "사용자 알림 허용 여부를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class, examples = "true"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<Boolean> allow(CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[온보딩] 대학교 저장", description = "사용자가 선택한 대학 정보를 저장하는 API")
    @Parameter(name = "name", description = "대학교 이름", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "success"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class))),
            @ApiResponse(responseCode = "40402", description = "대학교 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> patchUniv(String name, CustomUserDetails userDetails, HttpServletResponse response);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[온보딩] fcm token 저장", description = "사용자가 알림을 수락하면 fcm token을 저장하는 API")
    @Parameter(name = "token", description = "사용자 fcm token", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "success"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> patchToken(String token, CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[온보딩, 설정] 알림 허용 설정", description = "사용자의 알림 허용 여부를 변경하는 API")
    @Parameter(name = "allow", description = "사용자 알림 허용 여부", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "success"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> patchAllow(Boolean allow, CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[알림] 알림 목록", description = "사용자가 받은 알림 목록을 조회하는 API")
    @Parameter(name = "cursor", description = "알림 목록 마지막 요소의 Id", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PushLogPageRes.class))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<PushLogPageRes> showPushLogs(Long cursor, CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[키워드] 사용자 키워드 추가", description = "사용자의 키워드를 등록하는 API")
    @Parameter(name = "keyword", description = "사용자가 등록할 키워드", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> addKeyword(String keyword, CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[키워드] 사용자 키워드 목록", description = "사용자가 설정한 키워드를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserKeywordsRes.class))
                    )),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<List<UserKeywordsRes>> userKeywords(CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[키워드] 사용자 키워드 삭제", description = "사용자가 선택한 키워드를 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, examples = "success"))),
            @ApiResponse(responseCode = "40401", description = "사용자 찾기 실패", content = @Content(schema =  @Schema(implementation = ErrorCode.class)))
    })
    public BaseResponse<String> deleteKeyword(Long notifyId, CustomUserDetails userDetails);
}
