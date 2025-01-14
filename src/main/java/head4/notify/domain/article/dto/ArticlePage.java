package head4.notify.domain.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ArticlePage {
    @Schema(description = "공지의 마지막 요소의 Id")
    private Long cursor;

    @Schema(description = "다음 페이지가 존재하는지 여부")
    private boolean hasNext;

    @Schema(description = "공지 목록")
    private List<ArticleInfo> articles = new ArrayList<>();
}
