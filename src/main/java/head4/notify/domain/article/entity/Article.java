package head4.notify.domain.article.entity;

import head4.notify.customResponse.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Article extends BaseTime {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "univId")
    private int univId;

    private String title;

    private String url;

    private String num;

    public Article(int univId, String title, String url, String num) {
        this.univId = univId;
        this.title = title;
        this.url = url;
        this.num = num;
    }
}
