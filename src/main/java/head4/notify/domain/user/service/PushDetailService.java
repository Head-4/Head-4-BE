package head4.notify.domain.user.service;

import head4.notify.domain.article.dto.ArticleInfo;
import head4.notify.domain.article.dto.ArticlePage;
import head4.notify.domain.user.dto.PushLog;
import head4.notify.domain.user.dto.PushLogPage;
import head4.notify.domain.user.repository.PushDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PushDetailService {
    private final PushDetailRepository pushDetailRepository;

    // TODO: 10개 단위로 커서 페이징 구현
    public PushLogPage getArticleList(Long cursor, Long userId) {
        PageRequest pageRequest =
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        Page<PushLog> page;

        if(cursor == 0) {
            page = pushDetailRepository.getPushDetailPage(userId, pageRequest);
        } else {
            page = pushDetailRepository.getPushDetailPage(cursor, userId, pageRequest);
        }

        List<PushLog> pushLogs = page.getContent();

        return new PushLogPage(pushLogs.get(pushLogs.size() - 1).getPushId(), page.hasNext(), pushLogs);
    }
}
