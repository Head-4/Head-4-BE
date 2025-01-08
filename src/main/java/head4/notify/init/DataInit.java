package head4.notify.init;

import head4.notify.domain.article.entity.University;
import head4.notify.domain.notification.repository.UniversityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UniversityRepository universityRepository;

    @PostConstruct
    public void init() {
        String[] names = {"KJU", "KORNU", "SMU", "BU", "SCH", "KTU", "HSU"};
        List<University> universities = new ArrayList<>();

        for (String name : names) {
            universities.add(new University(name, 1));
        }
        universityRepository.saveAll(universities);
    }
}
