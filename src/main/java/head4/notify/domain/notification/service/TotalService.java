package head4.notify.domain.notification.service;

import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.service.ArticleService;
import head4.notify.domain.notification.entity.NotifyArticle;
import head4.notify.domain.notification.entity.dto.NotifyDetail;
import head4.notify.domain.notification.entity.dto.NotifyIdProjection;
import head4.notify.domain.notification.entity.embedded.NotifyArticleId;
import head4.notify.domain.notification.repository.NotifyArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TotalService {

    private final NotifyService notifyService;

    private final ArticleService articleService;

    private final NotifyArticleRepository notifyArticleRepository;

    public List<NotifyDetail> createTotal(CreateArticleRequest request) {
        StopWatch stopWatch = new StopWatch();

        // 크롤링한 공지 엔티티로 변환 후 저장, 식별자 받아오기
        stopWatch.start();
        List<Long> articleIds = articleService.create(request);

        // 크롤링한 공지중에서 해당 학교 키워드와 매칭되는 알림 객체 식별자 조회
        List<NotifyDetail> notifyDetails = notifyService.matchNotify(articleIds);


        // 푸시 알림 전송
        notifyDetails.forEach(detail -> {
            if(!detail.getFcmToken().equals("token")) {
                notifyService.sendPushMessage(detail);
            }
            System.out.println("detail = " + detail);
        });

        stopWatch.stop();
        log.info("알림 개수: {}  조회 시간: {}ms", notifyDetails.size(), stopWatch.getTotalTimeMillis());

        return notifyDetails;
    }
}
