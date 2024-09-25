package ukma.speedyfix.service.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.merger.UserMerger;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements MyAuthService<UserEntity> {

    private final MyValidator<UserEntity> validator;
    private final UserRepository repository;
    private final UserMerger merger;

    public UserEntity getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

    public List<UserEntity> getList(Map<String, Object> criteria) {
        return repository.findAll();
    }

    public Integer create(UserEntity view) {
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
