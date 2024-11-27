package ukma.speedyfix.service.user;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.merger.UserMerger;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements MyService<UserEntity, UserEntity, Integer>, MyAuthService<UserEntity> {

    private final MyValidator<UserEntity> validator;
    private final UserRepository repository;
    private final UserMerger merger;
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Override
    public UserEntity getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchEntityException("User with id: " + id + " not found"));
    }

    public List<UserEntity> getList() {
        return repository.findAll();
    }

    public Integer create(UserEntity view) {
        validator.validForCreate(view);
        String encryptedPassword = new BCryptPasswordEncoder().encode(view.getPassword());
        view.setPassword(encryptedPassword);
        view = repository.saveAndFlush(view);
        return view.getId();
    }

    public boolean update(UserEntity view) {
        UserEntity entity = getById(view.getId());
        merger.merge(entity, view);
        validator.validForUpdate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    public boolean delete(Integer id) {
        UserEntity entity = getById(id);
        validator.validForDelete(entity);
        repository.delete(entity);
        return true;
    }

    @Override
    public Optional<UserEntity> findByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
}
