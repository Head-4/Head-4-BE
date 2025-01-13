package head4.notify.domain.user.entity;

import head4.notify.domain.article.entity.University;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "univ_id")
    private int univId;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String fcmToken;

    public User(String email, RoleType roleType) {
        this.email = email;
        this.roleType = roleType;
        this.fcmToken = "token";
    }

    public void setUnivId(int univId) {
        this.univId = univId;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
