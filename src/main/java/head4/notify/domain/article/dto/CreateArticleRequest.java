package head4.notify.domain.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateArticleRequest {

    @Schema(description = "대학교 이름", example = "SMU")
    private String school;

    private List<ArticleDetail> articleDetails = new ArrayList<>();
}
