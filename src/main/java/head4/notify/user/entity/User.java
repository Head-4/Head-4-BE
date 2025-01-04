package head4.notify.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String email;

    @Enumerated(EnumType.STRING)
    RoleType roleType;

    public User(String email, RoleType roleType) {
        this.email = email;
        this.roleType = roleType;
    }
}
