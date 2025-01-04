package head4.notify.oauth.controller;

import head4.notify.customResponse.ApiResponse;
import head4.notify.oauth.kakao.KakaoDto;
import head4.notify.oauth.kakao.KakaoUtil;
import head4.notify.oauth.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ApiResponse<Long> kakaoLogin(@PathVariable(value = "code") String code,
                                  HttpServletResponse response) {
        Long userId = oAuthService.kakaoOAuthLogin(code, response);
        return ApiResponse.ok(userId);
    }
}
