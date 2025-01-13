package head4.notify.domain.article.controller.docs;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.article.dto.ArticlePage;
import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.notification.entity.dto.NotifyDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Article API",  description = "공지사항 관련 기능 API")
public interface ArticleControllerDocs {
    @Operation(summary = "공지 생성", description = "크롤링한 공지를 전송하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
    })
    public List<NotifyDetail> create(CreateArticleRequest request);

    @Operation(summary = "공지 페이징", description = "공지 10개 단위로 페이징 하는 API")
    @Parameter(name = "cursor", description = "보내준 마지막 공지의 id", in = ParameterIn.PATH)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ArticlePage.class))),
    })
    public BaseResponse<ArticlePage> articleList(Long cursor);
}
