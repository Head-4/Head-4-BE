package head4.notify.oauth.service;

import head4.notify.oauth.kakao.KakaoDto;
import head4.notify.oauth.kakao.KakaoUtil;
import head4.notify.security.custom.CustomUserInfoDto;
import head4.notify.security.jwt.JwtUtil;
import head4.notify.domain.user.entity.RoleType;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {
    private final JwtUtil jwtUtil;

    private final KakaoUtil kakaoUtil;

    private final UserRepository userRepository;

    public Long kakaoOAuthLogin(String code, HttpServletResponse response) {
        KakaoDto.OAuthToken oAuthToken = kakaoUtil.getAccessToken(code);
        KakaoDto.UserInfo userInfo = kakaoUtil.getUserInfo(oAuthToken.getAccess_token());

        String kakaoEmail = userInfo.getKakao_account().getEmail();

        // 해당 이메일로 가임된 유저인지 확인
        // 가입된 사용자 -> 해당 엔티티 반환
        // 가입되지 않은 사용자 -> join()을 통해 새로운 User 생성
        User user = userRepository.findByEmail(kakaoEmail)
                .orElseGet(() -> join(kakaoEmail));

        // 사용자 jwt 토큰 생성하기
        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getEmail(), user.getRoleType());
        String accessToken = jwtUtil.createAccessToken(customUserInfoDto);

        response.setHeader("Authorization", accessToken);
        return user.getId();
    }

    // 가입이 안된 사용자 회원가입 기능
    private User join(String email) {
        User user = new User(email, RoleType.ROLE_USER);
        return userRepository.save(user);
    }
}
