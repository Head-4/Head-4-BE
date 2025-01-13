package head4.notify.domain.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class University {
    @Id
    @Column(name = "univ_id")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    private int campus;

    public University(String name, int campus) {
        this.name = name;
        this.campus = campus;
    }
}
