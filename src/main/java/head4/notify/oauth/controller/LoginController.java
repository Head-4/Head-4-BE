package head4.notify.oauth.controller;

import head4.notify.oauth.kakao.KakaoDto;
import head4.notify.oauth.kakao.KakaoUtil;
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

    @PostMapping("/kakao/{code}")
    public String kakaoLogin(@PathVariable(value = "code") String code) {
        System.out.println("code = " + code);

        return "";
    }
}
