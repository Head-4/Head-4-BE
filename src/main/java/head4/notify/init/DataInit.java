package head4.notify.init;

import head4.notify.domain.article.entity.University;
import head4.notify.domain.article.repository.UniversityRepository;
import head4.notify.domain.notification.entity.Notify;
import head4.notify.domain.notification.repository.NotifyRepository;
import head4.notify.domain.notification.service.NotifyService;
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

    private final NotifyService notifyService;

    private final UserRepository userRepository;

    private final UniversityRepository universityRepository;

    private final NotifyRepository notifyRepository;

    @PostConstruct
    public void init() {
        // 대학교 생성
        String[] names = {"상명대학교", "백석대학교", "나사렛대학교", "순천향대학교", "호서대학교"};
        List<University> universities = new ArrayList<>();

        for (String name : names) {
            universities.add(new University(name, 0));
        }
        universities.add(new University("상명대학교 천안캠퍼스", 2));
        universities.add(new University("상명대학교 서울캠퍼스", 1));

        universities = universityRepository.saveAll(universities);

        // 사용자 생성
        String[] emails = {"상명서울", "백석이", "나사렛"};
        List<User> users = new ArrayList<>();

        for (String email : emails) {
            users.add(new User(email, RoleType.ROLE_USER));
        }
        for(int i = 1; i <= 30; i++) {
            users.add(new User("상명천안" + i, RoleType.ROLE_USER));
        }

        for(int i = 0; i < emails.length; i++) {
            users.get(i).setUnivId(universities.get(i).getId());
        }
        for(int i = emails.length; i < users.size(); i++) {
            users.get(i).setUnivId(universities.get(5).getId());
        }
        users = userRepository.saveAll(users);

        // 알림 추가
        String[] keywords = {"장학", "근로", "취업", "실습", "수상"};

        for (String keyword : keywords) {
            users.forEach(user -> {
                notifyService.create(user.getId(), keyword);
            });
        }
    }
}
