package head4.notify.domain.article.repository;

import head4.notify.domain.article.dto.ArticleInfo;
import head4.notify.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // TODO: 커서 기반 조회
    @Query("select new head4.notify.domain.article.dto.ArticleInfo(" +
            "a.id, a.title, a.url, a.createdDate) " +
            "from Article a " +
            "where a.id < :cursor and a.univId = :univId " +
            "and a.title like concat('%', :keyword, '%')")
    Page<ArticleInfo> getArticleList(@Param("cursor") Long cursor,
                                     @Param("univId") int univId,
                                     @Param("keyword") String keyword,
                                     Pageable pageable);
    @Query("select new head4.notify.domain.article.dto.ArticleInfo(" +
            "a.id, a.title, a.url, a.createdDate) " +
            "from Article a " +
            "where a.id < :cursor and a.univId = :univId")
    Page<ArticleInfo> getArticleList(@Param("cursor") Long cursor,
                                     @Param("univId") int univId,
                                     Pageable pageable);

    // TODO: 첫 페이지 조회
    @Query("select new head4.notify.domain.article.dto.ArticleInfo(" +
            "a.id, a.title, a.url, a.createdDate) " +
            "from Article a " +
            "where a.univId = :univId")
    Page<ArticleInfo> getArticleList(@Param("univId") int univId, Pageable pageable);

    @Query("select new head4.notify.domain.article.dto.ArticleInfo(" +
            "a.id, a.title, a.url, a.createdDate) " +
            "from Article a " +
            "where a.univId = :univId " +
            "and a.title like concat('%', :keyword, '%')")
    Page<ArticleInfo> getArticleList(@Param("univId") int univId,
                                     @Param("keyword") String keyword,
                                     Pageable pageable);


}
