package head4.notify.oauth.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KakaoUtil {
    @Value("${kakao.auth.client}")
    private String client;

    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private final String GRANT_TYPE = "authorization_code";

    // 받은 인가코드를 사용하여 액세스 토큰 발급
    public KakaoDto.OAuthToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", CONTENT_TYPE);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", client);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        HttpEntity<LinkedMultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoRequest,
                String.class);

        KakaoDto.OAuthToken oAuthToken = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoDto.OAuthToken.class);
            log.info("oAuthToken : {}", oAuthToken.getAccess_token());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.JSON_PARSING_ERROR);
        }

        return oAuthToken;
    }

    // 발급 받은 액세스 토큰을 사용하여 카카오에서 사용자 정보 가져오기

}
