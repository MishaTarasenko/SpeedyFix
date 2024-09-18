package ukma.speedyfix.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements MyService<UserEntity, Integer>, MyAuthService<UserEntity> {

    private final MyValidator<UserEntity> validator;

    @Autowired
    public UserService(MyValidator<UserEntity> validator) {
        this.validator = validator;
    }

    @Override
    public UserEntity getById(Integer id) {
        return null;
    }

    @Override
    public List<UserEntity> getList(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public Integer create(UserEntity view) {
        return 0;
    }

    @Override
    public boolean update(UserEntity view) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Optional<UserEntity> findByEmail(String userEmail) {
        return Optional.empty();
    }
}
