package head4.notify.domain.article.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Article {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "univ_id")
    private University university;

    private String title;

    private String url;

    private String num;

    public Article(University university, String title, String url, String num) {
        this.university = university;
        this.title = title;
        this.url = url;
        this.num = num;
    }
}
