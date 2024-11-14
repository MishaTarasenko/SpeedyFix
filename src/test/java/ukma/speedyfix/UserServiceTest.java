package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.user.UserService;

@SpringBootTest
@Import({UserService.class})
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    void getAllUserTest(){
        userService.getList();
    }

    @Test
    void createDeleteFindByEmailTest(){
        UserEntity user = new UserEntity(null, "Test", "Test", "test@test.com", "0000000000", "12345");
        UserEntity resFindByEmail = userService.findByEmail(user.getEmail()).orElse(null);
        assert(resFindByEmail == null);
        userService.create(user);
        resFindByEmail = userService.findByEmail(user.getEmail()).orElse(null);
        assert(resFindByEmail != null);
        userService.delete(resFindByEmail.getId());
        resFindByEmail = userService.findByEmail(user.getEmail()).orElse(null);
        assert(resFindByEmail == null);
    }
}
