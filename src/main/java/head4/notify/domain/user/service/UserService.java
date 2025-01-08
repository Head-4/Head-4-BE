package head4.notify.domain.user.service;

import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.repository.UserRepository;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
