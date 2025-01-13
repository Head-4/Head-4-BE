package head4.notify.domain.user.service;

import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.domain.article.service.UniversityService;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.repository.UserNotifyRepository;
import head4.notify.domain.user.repository.UserRepository;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    private final UniversityService universityService;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void patchUnivId(Long userId, String univName) {
        User user = getUserById(userId);
        Integer univId = universityService.getUnivId(univName);

        user.setUnivId(univId);
    }
}
