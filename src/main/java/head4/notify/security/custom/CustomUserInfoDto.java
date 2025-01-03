package head4.notify.security.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long userId;

    private String email;

    private String nickname;

    private String password;

    public CustomUserInfoDto(Long userId) {
        this.userId = userId;
    }
}
