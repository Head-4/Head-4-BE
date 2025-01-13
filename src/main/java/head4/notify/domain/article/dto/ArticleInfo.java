package head4.notify.domain.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleInfo {
    @Schema(description = "공지 Id")
    private Long id;

    @Schema(description = "공지 제목")
    private String title;

    @Schema(description = "공지 URL")
    private String url;

    @Schema(description = "공지 올라온 날짜")
    private LocalDateTime date;
}
