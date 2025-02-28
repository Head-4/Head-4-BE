package head4.notify.oauth.service;

import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import head4.notify.oauth.kakao.KakaoDto;
import head4.notify.oauth.kakao.KakaoUtil;
import head4.notify.security.custom.CustomUserInfoDto;
import head4.notify.security.jwt.JwtUtil;
import head4.notify.domain.user.entity.RoleType;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {
    private final JwtUtil jwtUtil;

    private final KakaoUtil kakaoUtil;

    private final UserRepository userRepository;

    @Transactional
    public Boolean kakaoOAuthLogin(String code, HttpServletResponse response) {
        KakaoDto.OAuthToken oAuthToken = kakaoUtil.getAccessToken(code);
        KakaoDto.UserInfo userInfo = kakaoUtil.getUserInfo(oAuthToken.getAccess_token());

        Boolean newUser = false;
        Long kakaoId = userInfo.getId();
        String kakaoEmail = userInfo.getKakao_account().getEmail();

        // 해당 이메일로 가임된 유저인지 확인
        // 가입된 사용자 -> 해당 엔티티 반환
        // 가입되지 않은 사용자 -> join()을 통해 새로운 User 생성
        User user = null;
        Optional<User> optionalUser = userRepository.findByEmail(kakaoEmail);

        if(!optionalUser.isPresent()) {
            user = join(kakaoId, kakaoEmail);
            newUser = true;
        } else {
            user = optionalUser.get();
        }

        // 사용자 jwt 토큰 생성하기
        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getUnivId(), user.getEmail(), user.getRoleType());
        String accessToken = jwtUtil.createAccessToken(customUserInfoDto);
        ResponseCookie cookie = jwtUtil.createCookie(accessToken);

        response.addHeader("Set-Cookie", cookie.toString());
        response.setHeader("Authorization", accessToken);
        return newUser;
    }

    public String logout(HttpServletResponse response) {
        ResponseCookie cookie = jwtUtil.deleteCookie();
        response.addHeader("Set-Cookie", cookie.toString());
        return "success";
    }

    public String user1Login(HttpServletResponse response) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자 jwt 토큰 생성하기
        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getUnivId(), user.getEmail(), user.getRoleType());
        String accessToken = jwtUtil.createAccessToken(customUserInfoDto);
        ResponseCookie cookie = jwtUtil.createLocalCookie(accessToken);

        response.addHeader("Set-Cookie", cookie.toString());
        return accessToken;
    }

    // 가입이 안된 사용자 회원가입 기능
    private User join(Long kakaoId, String email) {
        User user = new User(kakaoId, email, RoleType.ROLE_USER);
        return userRepository.save(user);
    }
}
