package head4.notify.domain.user.service;

import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.domain.article.service.UniversityService;
import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.repository.NotifyRepository;
import head4.notify.domain.user.dto.UserKeywordsRes;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.entity.UserNotify;
import head4.notify.domain.user.entity.embedded.UserNotifyId;
import head4.notify.domain.user.repository.PushDetailRepository;
import head4.notify.domain.user.repository.UserNotifyRepository;
import head4.notify.domain.user.repository.UserRepository;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import head4.notify.oauth.kakao.KakaoUtil;
import head4.notify.security.custom.CustomUserInfoDto;
import head4.notify.security.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static head4.notify.exceoption.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    private final UserNotifyRepository userNotifyRepository;

    private final NotifyRepository notifyRepository;

    private final UniversityService universityService;

    private final UniversityRepository universityRepository;

    private final PushDetailRepository pushDetailRepository;

    private final JwtUtil jwtUtil;

    private final KakaoUtil kakaoUtil;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    public String getEmail(Long userId) {
        User user = getUserById(userId);
        return user.getEmail();
    }

    public String getUniversity(Long userId) {
        User user = getUserById(userId);
        return universityRepository.getUnivName(user.getUnivId());
    }

    public Boolean getAllow(Long userId) {
        User user = getUserById(userId);
        return Boolean.valueOf(user.getNotifyAllow());
    }

    @Transactional
    public void patchUnivId(Long userId, String univName, HttpServletResponse response) {
        User user = getUserById(userId);
        Integer univId = universityService.getUnivId(univName);

        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), univId, user.getEmail(), user.getRoleType());
        String accessToken = jwtUtil.createAccessToken(customUserInfoDto);
        ResponseCookie cookie = jwtUtil.createCookie(accessToken);

        response.addHeader("Set-Cookie", cookie.toString());

        user.setUnivId(univId);
    }

    @Transactional
    public void patchFcmToken(Long userId, String token) {
        User user = getUserById(userId);
        user.setFcmToken(token);
        user.setNotifyAllow(true);
    }

    @Transactional
    public void patchAllow(Long userId, Boolean allow) {
        User user = getUserById(userId);
        user.setNotifyAllow(allow);
    }

    @Transactional
    public void addKeyword(Long userId, String keyword) {
        User user = getUserById(userId);

        Notify notify = notifyRepository.findNotifyByUnivIdAndKeyword(user.getUnivId(), keyword)
                .orElseGet(() -> notifyRepository.save(new Notify(user.getUnivId(), keyword)));

        UserNotifyId userNotifyId = new UserNotifyId(userId, notify.getId());
        userNotifyRepository.save(new UserNotify((userNotifyId)));
    }

    public List<UserKeywordsRes> getKeywords(Long userId) {
        return userNotifyRepository.findUserKeywords(userId);
    }

    @Transactional
    public void deleteKeyword(Long userId, Long notifyId) {
        UserNotify userNotify = userNotifyRepository.findByNotifyIdAndUserId(userId, notifyId)
                .orElseThrow(() -> new CustomException(KEYWORD_NOT_FOUND));

        userNotifyRepository.delete(userNotify);
    }

    public Boolean checkFcmToken(Long userId) {
        User user = getUserById(userId);
        return user.getFcmToken() != null;
    }

    @Transactional
    public Boolean withdrawal(Long userId) {
        // 등록한 키워드 삭제
        userNotifyRepository.deleteByUserId(userId);

        // 푸시 알림 로그 삭제
        pushDetailRepository.deleteByUserId(userId);

        // 카카오 연결 끊기
        User user = getUserById(userId);
        kakaoUtil.kakaoUnlink(user.getKakaoId());

        userRepository.delete(user);
        return true;
    }

    public String validateCookie(String accessToken) {
        if(accessToken == null || !jwtUtil.validateToken(accessToken)) {
            throw new CustomException(JWT_EXPIRED_TOKEN_ERROR);
        }

        return accessToken;
    }
}
