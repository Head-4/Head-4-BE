package head4.notify.domain.notification.service;

import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.repository.NotifyRepository;
import head4.notify.domain.user.entity.User;
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

    @Transactional
    public void create(Long userId, String keyword) {
        User user = userService.getUserById(userId);

        // 사용자의 대학교에 키워드가 등록되어 있는지 확인하기
        if(!notifyRepository.existsByUnivIdAndKeyword(user.getUnivId(), keyword)) {
            // 없다면 추가하기
            notifyRepository.save(new Notify(user.getUnivId(), keyword));
        }
    }
}
