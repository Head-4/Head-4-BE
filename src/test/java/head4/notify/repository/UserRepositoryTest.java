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

        for(int i = 1; i <= 100; i++) {
            users.add(new User("test" + i, RoleType.ROLE_USER));
        }

        stopWatch.start();
        userRepository.saveAll(users);
        stopWatch.stop();

        //   100 -> 114ms
        //  1000 -> 335ms
        // 10000 -> 1600ms
        System.out.println("수행시간: " + stopWatch.getTotalTimeMillis() + "ms");
    }

    @Test
    public void saveAllJdbc() {
        StopWatch stopWatch = new StopWatch();
        List<User> users = new ArrayList<>();

        for(int i = 1; i <= 100; i++) {
            users.add(new User("test" + i, RoleType.ROLE_USER));
        }

        stopWatch.start();
        userBulkRepository.saveAllJdbc(users);
        stopWatch.stop();

        //   100 -> 15ms
        //  1000 -> 22ms
        // 10000 -> 76ms
        System.out.println("수행시간: " + stopWatch.getTotalTimeMillis() + "ms");
    }
}
