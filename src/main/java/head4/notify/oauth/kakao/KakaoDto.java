package head4.notify.oauth.kakao;

import lombok.Getter;

@Getter
public class KakaoDto {

    @Getter
    public static class OAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private int refresh_token_expires_in;
    }
}
