package ukma.speedyfix.service.user;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.BaseValidator;

@Component
public class UserValidator extends BaseValidator<UserEntity> {

    public UserValidator() {
        super(UserEntity.class);
    }
}
