package head4.notify.domain.article.service;

import head4.notify.domain.article.dto.ArticleDetail;
import head4.notify.domain.article.dto.ArticleInfo;
import head4.notify.domain.article.dto.ArticlePage;
import head4.notify.domain.article.dto.CreateArticleRequest;
import head4.notify.domain.article.entity.Article;
import head4.notify.domain.article.entity.University;
import head4.notify.domain.article.repository.ArticleRepository;
import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.grpc.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
            if(universities.size() > 1 && detail.getCampus() == 0) {
                if(checkDuplicate(detail.getArticle_num(), universities.get(1).getId())) continue;

                for(int i = 1; i < universities.size(); i++) {
                    articles.add(new Article(
                            universities.get(i).getId(),
                            detail.getTitle(),
                            detail.getArticle_url(),
                            detail.getArticle_num()));
                }
            }
            else {
                if(checkDuplicate(detail.getArticle_num(), universities.get(detail.getCampus()).getId())) continue;

                articles.add(new Article(
                        universities.get(detail.getCampus()).getId(),
                        detail.getTitle(),
                        detail.getArticle_url(),
                        detail.getArticle_num()));
            }
        }

        articles = articleRepository.saveAll(articles);

        return articles.stream().map(article -> article.getId()).toList();
    }

    @Transactional
    public List<Long> createGrpc(ArticleRequest request) {
        String univName = request.getSchool();

        // 해당 대학교(캠퍼스 포함) 엔티티 가져오기
        // 캠퍼스 기준 오름차순 정렬 0 1 2 형식
        List<University> universities = universityRepository.findUniversitiesByName(univName);

        List<Article> articles = new ArrayList<>();
        List<head4.notify.grpc.ArticleDetail> articleDetails = request.getArticleDetailsList();

        for (head4.notify.grpc.ArticleDetail detail : articleDetails) {

            if(universities.size() > 1 && detail.getCampus() == 0) {
                if(checkDuplicate(detail.getArticleNum(), universities.get(1).getId())) continue;

                for(int i = 1; i < universities.size(); i++) {
                    articles.add(new Article(
                            universities.get(i).getId(),
                            detail.getTitle(),
                            detail.getArticleUrl(),
                            detail.getArticleNum()));
                }
            }
            else {
                if(checkDuplicate(detail.getArticleNum(), universities.get(detail.getCampus()).getId())) continue;

                articles.add(new Article(
                        universities.get(detail.getCampus()).getId(),
                        detail.getTitle(),
                        detail.getArticleUrl(),
                        detail.getArticleNum()));
            }
        }

        articles = articleRepository.saveAll(articles);
        return articles.stream().map(article -> article.getId()).toList();
    }

    @Transactional
    public List<University> create2(CreateArticleRequest request) {
        String univName = request.getSchool();

        // 해당 대학교(캠퍼스 포함) 엔티티 가져오기
        // 캠퍼스 기준 오름차순 정렬 0 1 2 형식
        List<University> universities = universityRepository.findUniversitiesByName(univName);

        List<Article> articles = new ArrayList<>();
        List<ArticleDetail> articleDetails = request.getArticleDetails();

        // TODO: try catch 로 잘못된 인덱스 오류 체크 추가
        // 캠퍼스가 1 ~ 이면 공통공지도 알림으로 찾는 로직
        for (ArticleDetail detail : articleDetails) {
            if(universities.size() > 1 && detail.getCampus() == 0) {
                if(checkDuplicate(detail.getArticle_num(), universities.get(1).getId())) continue;

                for(int i = 1; i < universities.size(); i++) {
                    articles.add(new Article(
                            universities.get(i).getId(),
                            detail.getTitle(),
                            detail.getArticle_url(),
                            detail.getArticle_num()));
                }
            }
            else {
                if(checkDuplicate(detail.getArticle_num(), universities.get(detail.getCampus()).getId())) continue;

                articles.add(new Article(
                        universities.get(detail.getCampus()).getId(),
                        detail.getTitle(),
                        detail.getArticle_url(),
                        detail.getArticle_num()));
            }
        }
        if(universities.size() > 1) universities.remove(0);
        articleRepository.saveAll(articles);
        return universities;
    }

    // 존재하는 공지인지 확인
    private boolean checkDuplicate(String num, Integer univId) {
        return articleRepository.existsByNumAndUnivId(num, univId);
    }

    // TODO: 10개 단위로 커서 페이징 구현
    public ArticlePage getArticleList(Long cursor, int univId, String keyword) {
        PageRequest pageRequest =
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        Page<ArticleInfo> page;

        if(cursor == 0) {
            page = keyword.equals("null") ?
                    articleRepository.getArticleList(univId, pageRequest) :
                    articleRepository.getArticleList(univId, keyword, pageRequest);
        } else {
            page = keyword.equals("null") ?
                    articleRepository.getArticleList(cursor, univId, pageRequest) :
                    articleRepository.getArticleList(cursor, univId, keyword, pageRequest);
        }

        List<ArticleInfo> articles = page.getContent();

        return articles.isEmpty() ?
                new ArticlePage(0L, false, articles) :
                new ArticlePage(articles.get(articles.size() - 1).getId(), page.hasNext(), articles);
    }
}