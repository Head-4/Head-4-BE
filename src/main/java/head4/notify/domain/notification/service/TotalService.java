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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TotalService {

    private final NotifyService notifyService;

    private final ArticleService articleService;

    private final PushDetailJdbcRepository pushDetailJdbcRepository;

    private final NotifyArticleRepository notifyArticleRepository;

    public List<PushMessage> createTotal(CreateArticleRequest request) {
        StopWatch stopWatch = new StopWatch();

        // 크롤링한 공지 엔티티로 변환 후 저장, 식별자 받아오기
        stopWatch.start();
        List<Long> articleIds = articleService.create(request);

        // 크롤링한 공지중에서 해당 학교 키워드와 매칭되는 알림 객체 식별자 조회
        List<PushMessage> pushMessages = notifyService.matchNotify(articleIds);


        // 푸시 알림 전송 한 개당 150~200ms
        notifyService.sendPushMessage(pushMessages);

        // 푸시 알림 저장
        pushDetailJdbcRepository.batchSave(pushMessages);

        stopWatch.stop();

        //      sendEach -> 169ms
        // sendEachAsync ->  55ms
        log.info("알림 개수: {}  조회 시간: {}ms", pushMessages.size(), stopWatch.getTotalTimeMillis());

        return pushMessages;
    }

    public void createTotal2(CreateArticleRequest request) {
        StopWatch stopWatch = new StopWatch();

        // 크롤링한 공지 엔티티로 변환 후 저장, 식별자 받아오기
        stopWatch.start();
        //List<Long> articleIds = articleService.create(request);

        // 크롤링한 공지중에서 해당 학교 키워드와 매칭되는 알림 객체 식별자 조회
        //List<PushMessage> pushMessages = notifyService.matchNotify(articleIds);

        List<ArticleDetail> articles = request.getArticleDetails();
        List<University> universities = articleService.create2(request);

        for (University university : universities) {
            // 해당 대학 알림 조회
        }

        // 푸시 알림 전송 한 개당 150~200ms
        //notifyService.sendPushMessage(pushMessages);

        // 푸시 알림 저장
        //pushDetailJdbcRepository.batchSave(pushMessages);

        stopWatch.stop();

        //log.info("알림 개수: {}  조회 시간: {}ms", pushMessages.size(), stopWatch.getTotalTimeMillis());

        //return pushMessages;
    }
}
