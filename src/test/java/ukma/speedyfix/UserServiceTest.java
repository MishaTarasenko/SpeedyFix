package ukma.speedyfix;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.service.user.UserService;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    private final UserService service;
    private final UserRepository repository;

    @Autowired
    public UserServiceTest(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @AfterEach
    public void clearDB() {
        repository.deleteAll();
    }

    @Test
    void canCreateCorrectUsers() {
        for (UserEntity user : correctUsers) {
            service.create(user);
        }

        Assertions.assertEquals(3, repository.count());
    }

    @Test
    void cantCreateUserWithWrongEmail() {
        Assertions.assertThrows(ValidationException.class, () -> service.create(incorrectEmailUser));
    }

    @Test
    void cantCreateUserWithEmptyFields() {
        Assertions.assertThrows(ValidationException.class, () -> service.create(emptyPasswordUser));
    }

    @Test
    void canDeleteUser() {
        Integer id = service.create(correctUsers.getFirst());
        Assertions.assertTrue(repository.existsById(id));

        service.delete(id);
        Assertions.assertFalse(repository.existsById(id));
    }

    @Test
    void canUpdateUser() {
        Integer id = service.create(correctUsers.getFirst());
        Assertions.assertTrue(repository.existsById(id));

        service.update(new UserEntity(id, "Bob", "Johnson", null, null, null));
        UserEntity user = repository.findById(id).orElseThrow(AssertionError::new);
        Assertions.assertNotEquals(user.getFirstName(), correctUsers.getFirst().getFirstName());
        Assertions.assertNotEquals(user.getLastName(), correctUsers.getFirst().getLastName());
    }

    @Test
    void canGetUserList() {
        List<UserEntity> userList = List.copyOf(correctUsers);

        for (int i = 0; i < userList.size(); i++) {
            Integer id = service.create(correctUsers.get(i));
            userList.get(i).setId(id);
        }

        Assertions.assertEquals(userList, service.getList());
    }

    @Test
    void canGetUserById() {
        Integer id = service.create(correctUsers.getFirst());

        Assertions.assertNotNull(service.getById(id));
    }

    private final List<UserEntity> correctUsers = List.of(
            new UserEntity(null, "Alice", "Smith", "alice.smith@example.com", "1234567890", "password123"),
            new UserEntity(null, "Bob", "Johnson", "bob.johnson@example.com", "9876543210", "securePass456"),
            new UserEntity(null, "Carol", "Williams", "carol.williams@example.com", "4567890123", "Pa$$w0rd789")
    );

    private final UserEntity incorrectEmailUser = new UserEntity(null, "Alice", "Smith", "alice.example.com", "1234567890", "password123");
    private final UserEntity emptyPasswordUser = new UserEntity(null, "Alice", "Smith", "alice@example.com", "1234567890", "");
}
