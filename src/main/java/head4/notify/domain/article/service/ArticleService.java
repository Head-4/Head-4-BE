package head4.notify.domain.article.service;

import head4.notify.domain.article.dto.ArticleDetail;
import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.entity.Article;
import head4.notify.domain.article.entity.University;
import head4.notify.domain.article.repository.ArticleRepository;
import head4.notify.domain.article.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final UniversityRepository universityRepository;

    @Transactional
    public List<Long> create(CreateArticleRequest request) {
        String univName = request.getSchool();

        // 해당 대학교(캠퍼스 포함) 엔티티 가져오기
        // 캠퍼스 기준 오름차순 정렬 0 1 2 형식
        List<University> universities = universityRepository.findUniversitiesByName(univName);

        List<Article> articles = new ArrayList<>();
        List<ArticleDetail> articleDetails = request.getArticleDetails();

        // TODO: try catch 로 잘못된 인덱스 오류 체크 추가
        // 캠퍼스가 1 ~ 이면 공통공지도 알림으로 찾는 로직
        for (ArticleDetail detail : articleDetails) {
            if(universities.size() > 1 && detail.getCampus().equals(0)) {
                for(int i = 1; i < universities.size(); i++) {
                    articles.add(new Article(
                            universities.get(i).getId(),
                            detail.getTitle(),
                            detail.getArticle_url(),
                            detail.getArticle_num()));
                }
                continue;
            }

            articles.add(new Article(
                    universities.get(detail.getCampus()).getId(),
                    detail.getTitle(),
                    detail.getArticle_url(),
                    detail.getArticle_num()));
        }

        articles = articleRepository.saveAll(articles);

        return articles.stream().map(article -> article.getId()).toList();
    }
}
