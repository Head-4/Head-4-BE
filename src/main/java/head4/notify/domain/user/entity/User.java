package head4.notify.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public User(String email, RoleType roleType) {
        this.email = email;
        this.roleType = roleType;
    }
}