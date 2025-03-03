package head4.notify.domain.article.controller.docs;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.article.dto.ArticlePage;
import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.notification.entity.dto.PushMessage;
import head4.notify.security.custom.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(name = "Article API",  description = "공지사항 관련 기능 API")
public interface ArticleControllerDocs {
    @Operation(summary = "[크롤링] 공지 생성", description = "크롤링한 공지를 전송하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
    })
    public List<PushMessage> create(CreateArticleRequest request);

    @Operation(summary = "[메인] 공지 페이징 & 키워드 필터링 & 검색", description = "공지 10개 단위로 페이징 하는 API")
    @Parameters(value = {
            @Parameter(name = "cursor", description = "보내준 마지막 공지의 id", in = ParameterIn.PATH),
            @Parameter(name = "keyword", description = "검색 & 필터링 할 키워드(전체이면 null 전송)", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = ArticlePage.class))),
    })
    public BaseResponse<ArticlePage> articleList(Long cursor, String keyword, CustomUserDetails userDetails);

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @Operation(summary = "[테스트] 접근 권한 테스트", description = "접근 권한을 테스트하는 API")
    public String test();
}
