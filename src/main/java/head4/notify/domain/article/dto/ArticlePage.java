package head4.notify.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ArticlePage {
    private Long cursor;

    private boolean hasNext;

    private List<ArticleInfo> articles = new ArrayList<>();
}
