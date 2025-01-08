package head4.notify.domain.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "공지 세부 정보")
public class ArticleDetail {
    private String title;

    private String article_num;

    private String article_url;
}
