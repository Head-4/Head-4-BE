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
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final NotifyService notifyService;

    private final UserRepository userRepository;

    private final UniversityRepository universityRepository;

    private final NotifyRepository notifyRepository;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        // 대학교 생성
        String[] names = {"상명대학교", "백석대학교", "나사렛대학교", "순천향대학교", "호서대학교", "한국기술교육대학교", "연암대학교", "백석문화대학교"};
        List<University> universities = new ArrayList<>();

        universities.add(new University("상명대학교 천안캠퍼스", 2));
        universities.add(new University("상명대학교 서울캠퍼스", 1));
        for (String name : names) {
            universities.add(new University(name, 0));
        }

        universities = universityRepository.saveAll(universities);

        // 사용자 생성
//        String[] emails = {"상명이", "백석이", "나사렛"};
//        List<User> users = new ArrayList<>();
//
//        for(int i = 1; i <= 5; i++) {
//            users.add(new User(1L,"상명천안" + i, RoleType.ROLE_USER));
//        }
//        for (String email : emails) {
//            users.add(new User(1L, email, RoleType.ROLE_USER));
//        }
//
//        int size = users.size() - emails.length;
//
//        for(int i = 0; i < size; i++) {
//            users.get(i).setUnivId(universities.get(0).getId());
//        }
//        for(int i = size; i < users.size(); i++) {
//            users.get(i).setUnivId(universities.get(i - size + 2).getId());
//        }
//
//        users = userRepository.saveAll(users);
//
//        // 알림 추가
//        String[] keywords = {"장학", "근로", "취업", "실습", "수상"};
//        //notifyService.create(users.get(0).getId(), "장학");
//        for (String keyword : keywords) {
//            users.forEach(user -> {
//                notifyService.create(user.getId(), keyword);
//            });
//        }



    }
}
