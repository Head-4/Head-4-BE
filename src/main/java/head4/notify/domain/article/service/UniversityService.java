package head4.notify.domain.article.service;

import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityService {
    private final UniversityRepository universityRepository;

    public Integer getUnivId(String name) {
        return universityRepository.getUnivId(name)
                .orElseThrow(() -> new CustomException(ErrorCode.UNIV_NOT_FOUND));
    }
}
