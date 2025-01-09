package head4.notify.domain.notification.service;

import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.service.ArticleService;
import head4.notify.domain.notification.entity.NotifyArticle;
import head4.notify.domain.notification.entity.dto.NotifyIdProjection;
import head4.notify.domain.notification.entity.embedded.NotifyArticleId;
import head4.notify.domain.notification.repository.NotifyArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TotalService {

    private final NotifyService notifyService;

    private final ArticleService articleService;

    private final NotifyArticleRepository notifyArticleRepository;

    @Transactional
    public void createTotal(CreateArticleRequest request) {
        // 크롤링한 공지 엔티티로 변환 후 저장, 식별자 받아오기
        List<Long> articleIds = articleService.create(request);

        // 크롤링한 공지중에서 해당 학교 키워드와 매칭되는 알림 객체 식별자 조회
        Set<Long> ids = notifyService.matchNotify(articleIds);
        List<NotifyArticle> notifyArticles = new ArrayList<>();

        ids.forEach(id ->
                System.out.println("notifyId = " + id));

        // TODO: 알림 식별자만 가져오면 사용자가 등록한 사용자_알림 테이블에서 가져오면 되나?



    }
}
