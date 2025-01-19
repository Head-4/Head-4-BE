package head4.notify.domain.article.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.article.controller.docs.ArticleControllerDocs;
import head4.notify.domain.article.dto.ArticlePage;
import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.service.ArticleService;
import head4.notify.domain.notification.entity.dto.PushMessage;
import head4.notify.domain.notification.service.TotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")

public class ArticleController implements ArticleControllerDocs {

    private final ArticleService articleService;
    private final TotalService totalService;

    @PostMapping("/create")
    public List<PushMessage> create(@RequestBody CreateArticleRequest request) {
        return totalService.createTotal(request);
    }

    // TODO: 공지 커서 페이징 구현
    @GetMapping("/page/{cursor}/{keyword}")
    public BaseResponse<ArticlePage> articleList(@PathVariable("cursor") Long cursor,
                                                 @PathVariable("keyword") String keyword) {
        ArticlePage page = articleService.getArticleList(cursor, 1, keyword);
        return BaseResponse.ok(page);
    }

    // TODO: 공지 검색 구현

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
