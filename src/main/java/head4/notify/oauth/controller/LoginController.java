package head4.notify.oauth.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.oauth.kakao.KakaoUtil;
import head4.notify.oauth.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {
    private final KakaoUtil kakaoUtil;
    private final OAuthService oAuthService;

    @PostMapping("/kakao/{code}")
    public BaseResponse<Long> kakaoLogin(@PathVariable(value = "code") String code,
                                         HttpServletResponse response) {
        Long userId = oAuthService.kakaoOAuthLogin(code, response);
        return BaseResponse.ok(userId);
    }

    @PostMapping("/test")
    public BaseResponse<?> test(HttpServletResponse response) {
        return BaseResponse.ok(oAuthService.user1Login(response));
    }
}
