package ukma.speedyfix.service.user;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.service.BaseValidator;

@Component
public class UserValidator extends BaseValidator<UserEntity> {

    private final UserRepository repository;

    public UserValidator(UserRepository userRepository) {
        super(UserEntity.class);
        this.repository = userRepository;
    }

    @Override
    public void validForCreate(UserEntity entity) {
        super.validForCreate(entity);

        if (repository.findByEmail(entity.getEmail()).isPresent()) {
            throw new ValidationException("User with email " + entity.getEmail() + " already exists");
        }
    }
}
