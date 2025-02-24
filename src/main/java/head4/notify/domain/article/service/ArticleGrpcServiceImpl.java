package head4.notify.domain.article.service;

import head4.notify.domain.article.entity.Article;
import head4.notify.domain.article.entity.University;
import head4.notify.domain.article.repository.ArticleRepository;
import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.domain.notification.entity.dto.PushMessage;
import head4.notify.domain.notification.service.NotifyService;
import head4.notify.domain.user.repository.PushDetailJdbcRepository;
import head4.notify.grpc.ArticleDetail;
import head4.notify.grpc.ArticleRequest;
import head4.notify.grpc.ArticleResponse;
import head4.notify.grpc.ArticleServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ArticleGrpcServiceImpl extends ArticleServiceGrpc.ArticleServiceImplBase {

    private final ArticleService articleService;

    private final NotifyService notifyService;

    private final PushDetailJdbcRepository pushDetailJdbcRepository;

    @Override
    public void createArticle(ArticleRequest request, StreamObserver<ArticleResponse> responseObserver) {
        List<Long> articleIds = articleService.createGrpc(request);

        // 크롤링한 공지중에서 해당 학교 키워드와 매칭되는 알림 객체 식별자 조회
        List<PushMessage> pushMessages = notifyService.matchNotify(articleIds);

        StopWatch stopWatch = new StopWatch();
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

        // 응답 생성
        ArticleResponse response = ArticleResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Articles received successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
