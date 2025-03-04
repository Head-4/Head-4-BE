package head4.notify.domain.notification.service;

import head4.notify.domain.article.dto.ArticleDetail;
import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.entity.University;
import head4.notify.domain.article.service.ArticleService;
import head4.notify.domain.notification.entity.dto.PushMessage;
import head4.notify.domain.notification.repository.NotifyArticleRepository;
import head4.notify.domain.user.repository.PushDetailJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TotalService {

    private final NotifyService notifyService;

    private final ArticleService articleService;

    private final PushDetailJdbcRepository pushDetailJdbcRepository;

    public List<PushMessage> createTotal(CreateArticleRequest request) {
        StopWatch stopWatch = new StopWatch();

        // 크롤링한 공지 엔티티로 변환 후 저장, 식별자 받아오기
        List<Long> articleIds = articleService.create(request);

        // 크롤링한 공지중에서 해당 학교 키워드와 매칭되는 알림 객체 식별자 조회
        List<PushMessage> pushMessages = notifyService.matchNotify(articleIds);

        stopWatch.start();
        // 푸시 알림 전송 한 개당 150~200ms
        notifyService.sendPushMessage(pushMessages);
        stopWatch.stop();

        //          send - > 1000개 67719ms
        //      sendEach ->  40개 124ms | 100개 140ms | 1000개 825ms
        // sendEachAsync ->  40개 140ms | 100개 150ms | 1000개 845ms
        log.info("알림 개수: {} 시간: {}ms", pushMessages.size(), stopWatch.getTotalTimeMillis());

        // 푸시 알림 저장
        pushDetailJdbcRepository.batchSave(pushMessages);
        return pushMessages;
    }
}
