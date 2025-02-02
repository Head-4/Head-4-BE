package head4.notify.repository;

import head4.notify.domain.user.entity.RoleType;
import head4.notify.domain.user.entity.User;
import head4.notify.domain.user.repository.UserBulkRepository;
import head4.notify.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@RequiredArgsConstructor
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBulkRepository userBulkRepository;

    @Test
    public void saveAllJpa() {
        StopWatch stopWatch = new StopWatch();
        List<User> users = new ArrayList<>();

        for(int i = 1; i <= 10000; i++) {
            users.add(new User("test" + i, RoleType.ROLE_USER));
        }

        stopWatch.start();
        userRepository.saveAll(users);
        stopWatch.stop();

        // 괄호는 도커 mariaDB 환경
        //   100 -> maria: 33ms(75ms) | mysql: 91ms
        //  1000 -> maria: 246ms(607ms) | mysql: 784ms
        // 10000 -> maria: 1517ms(5403ms) | mysql: 6233ms
        System.out.println("수행시간: " + stopWatch.getTotalTimeMillis() + "ms");
    }

    @Test
    public void saveAllJdbc() {
        StopWatch stopWatch = new StopWatch();
        List<User> users = new ArrayList<>();

        for(int i = 1; i <= 10000; i++) {
            users.add(new User("test" + i, RoleType.ROLE_USER));
        }

        stopWatch.start();
        userBulkRepository.saveAllJdbc(users);
        stopWatch.stop();

        //   100 -> maria: 6ms(7ms) | mysql: 69ms
        //  1000 -> maria: 11ms(18ms) | mysql: 549ms
        // 10000 -> maria: 58ms(106ms) | mysql: 5146ms
        System.out.println("수행시간: " + stopWatch.getTotalTimeMillis() + "ms");
    }
}
