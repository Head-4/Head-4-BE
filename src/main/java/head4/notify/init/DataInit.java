package head4.notify.init;

import head4.notify.domain.article.entity.University;
import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.domain.user.entity.RoleType;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    @PostConstruct
    public void init() {
        // 대학교 생성
        String[] names = {"상명대학교", "백석대학교", "나사렛대학교", "순천향대학교", "호서대학교"};
        List<University> universities = new ArrayList<>();

        for (String name : names) {
            universities.add(new University(name, 0));
        }
        universities.add(new University("상명대학교 천안캠퍼스", 2));

        universities = universityRepository.saveAll(universities);

        // 사용자 생성
        String[] emails = {"백석이", "나사렛"};
        List<User> users = new ArrayList<>();

        users.add(new User("상명천안", RoleType.ROLE_USER));
        for (String email : emails) {
            users.add(new User(email, RoleType.ROLE_USER));
        }

        users.get(0).setUnivId(universities.get(5).getId());
        for(int i = 1; i < 3; i++) {
            users.get(i).setUnivId(universities.get(i).getId());
        }

        userRepository.saveAll(users);
    }
}
