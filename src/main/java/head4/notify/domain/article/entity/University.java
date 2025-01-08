package head4.notify.domain.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class University {
    @Id
    @Column(name = "univ_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    private Integer campus;

    public University(String name, Integer campus) {
        this.name = name;
        this.campus = campus;
    }
}
