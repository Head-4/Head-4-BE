package head4.notify.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.entity.dto.NotifyDetail;
import head4.notify.domain.notification.entity.dto.NotifyIdProjection;
import head4.notify.domain.notification.entity.embedded.NotifyArticleId;
import head4.notify.domain.notification.repository.NotifyRepository;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import head4.notify.domain.user.repository.UserNotifyRepository;
import head4.notify.domain.user.service.UserService;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
    public List<NotifyDetail> matchNotify(List<Long> articleIds) {
         return notifyRepository.findMatchingNotify(articleIds);
    }

    // firebase 푸시 메세지 전송
    public void sendPushMessage(NotifyDetail detail) {
        try {
            String message = FirebaseMessaging.getInstance().send(
                    Message.builder()
                            .setNotification(Notification.builder()
                                    .setTitle(detail.getKeyword() + "새로운 공지")
                                    .setBody(detail.getTitle())
                                    .build()
                            )
                            .setToken(detail.getFcmToken())
                            .build());

            System.out.println("message = " + message);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FIREBASE_MESSAGE_ERROR);
        }
    }
}
