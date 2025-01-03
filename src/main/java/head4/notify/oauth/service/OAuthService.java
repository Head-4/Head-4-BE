package head4.notify.oauth.service;

import head4.notify.oauth.kakao.KakaoDto;
import head4.notify.oauth.kakao.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {
    private final KakaoUtil kakaoUtil;

    public void kakaoOAuthLogin(String code) {
        KakaoDto.OAuthToken oAuthToken = kakaoUtil.getAccessToken(code);
        // 사용자 정보 가져오는 코드 추가


    }
}
