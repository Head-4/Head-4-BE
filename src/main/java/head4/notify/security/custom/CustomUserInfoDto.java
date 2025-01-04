package head4.notify.security.custom;

import head4.notify.user.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.relation.Role;

@Getter
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long userId;

    private String email;

    private RoleType roleType;

    public CustomUserInfoDto(Long userId) {
        this.userId = userId;
    }
}
