package ukma.speedyfix.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.service.IValidator;

@Service
public class UserService {

    @Autowired
    public UserService(IValidator<UserEntity> validator) {}
}
