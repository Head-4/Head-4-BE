package head4.notify.domain.notification.service;

import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalService {
    private final ArticleService articleService;

    @Transactional
    public void createTotal(CreateArticleRequest request) {
        List<Long> articleIds = articleService.create(request);

    }
}
