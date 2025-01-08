package head4.notify.domain.notification.service;

import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.repository.NotifyRepository;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import head4.notify.domain.user.repository.UserNotifyRepository;
import head4.notify.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
