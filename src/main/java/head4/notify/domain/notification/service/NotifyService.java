package head4.notify.domain.notification.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.entity.dto.AddKeywordsRequest;
import head4.notify.domain.notification.entity.dto.PushMessage;
import head4.notify.domain.notification.repository.NotifyRepository;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import head4.notify.domain.user.repository.UserNotifyRepository;
import head4.notify.domain.user.service.UserService;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotifyService {
    private final UserService userService;

    private final NotifyRepository notifyRepository;

    private final UserNotifyRepository userNotifyRepository;

    @Transactional
    public void create(Long userId, String keyword) {
        User user = userService.getUserById(userId);

        // 사용자의 대학교에 해당 키워드로 등록된 알림이 있으면 해당 알림 객체 반환
        // 등록되지 않았으면 새로운 객체 생성 후 저장
        Notify notify = notifyRepository.findNotifyByUnivIdAndKeyword(user.getUnivId(), keyword)
                .orElseGet(() -> notifyRepository.save(new Notify(user.getUnivId(), keyword)));

        UserNotifyId userNotifyId = new UserNotifyId(userId, notify.getId());
        userNotifyRepository.save(new UserNotify(userNotifyId));
    }

    // 크롤링한 공지 제목에 해당 대학교에 등록된 키워드가 포함된 알림 식별자 조회
    public List<PushMessage> matchNotify(List<Long> articleIds) {
         return notifyRepository.findMatchingNotify(articleIds);
    }

    @Transactional
    public void addKeywords(Long userId, AddKeywordsRequest request) {
        User user = userService.getUserById(userId);

        List<String> keywords = request.getKeywords();
        List<UserNotify> userNotifies = new ArrayList<>();

        // 사용자의 대학교에 해당 키워드로 등록된 알림이 있으면 해당 알림 객체 반환
        // 등록되지 않았으면 새로운 객체 생성 후 저장
        keywords.forEach(keyword -> {
            Notify notify = notifyRepository.findNotifyByUnivIdAndKeyword(user.getUnivId(), keyword)
                    .orElseGet(() -> notifyRepository.save(new Notify(user.getUnivId(), keyword)));

            UserNotifyId userNotifyId = new UserNotifyId(userId, notify.getId());
            userNotifies.add(new UserNotify(userNotifyId));
        });

        userNotifyRepository.saveAll(userNotifies);
    }

    // firebase 푸시 메세지 전송
    public void sendPushMessage(List<PushMessage> pushMessages) {
        // TODO: sendAll 은 최대 500개의 메세지 처리 가능
        final int CHUNK_SIZE = 500;

        List<Message> messages = new ArrayList<>();

        for (PushMessage pushMessage : pushMessages) {
            messages.add(
                    Message.builder()
                            .setWebpushConfig(WebpushConfig.builder()
                                    .putHeader("Urgency", "high")
                                    .build())
                            .putData("title", "\'" + pushMessage.getKeyword() + "\'" + " 새로운 공지")
                            .putData("body", pushMessage.getTitle())
                            .putData("url", pushMessage.getUrl())
                            .setToken(pushMessage.getFcmToken())
                            .build()
            );

            // 500개씩 메세지 전송
            if(messages.size() % CHUNK_SIZE == 0) {
                sendFirebase(messages);
                messages.clear();
            }
        }

        // 500개씩 나눠서 보낸 후 나머지 메세지들
        if(!messages.isEmpty()) {
            sendFirebase(messages);
        }
    }

    private void sendFirebase(List<Message> messages) {
        try {
            ApiFuture<BatchResponse> future = FirebaseMessaging.getInstance().sendEachAsync(messages);
            BatchResponse batchResponse = future.get();

            List<Message> failedMessages = new ArrayList<>();

            for(int i = 0; i < batchResponse.getResponses().size(); i++) {
                SendResponse sendResponse = batchResponse.getResponses().get(i);

                if(!sendResponse.isSuccessful()) {
                    failedMessages.add(messages.get(i));
                    log.error("Failed to send message: {} - Error: {}",
                            messages.get(i).toString(),
                            sendResponse.getException().getMessage());
                }
            }

            if(!failedMessages.isEmpty()) {
                FirebaseMessaging.getInstance().sendEachAsync(failedMessages);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.FIREBASE_MESSAGE_SEND_ERROR);
        }
    }
}
