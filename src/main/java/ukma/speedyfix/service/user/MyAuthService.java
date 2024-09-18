package ukma.speedyfix.service.user;

import java.util.Optional;

public interface MyAuthService<T> {

    Optional<T> findByEmail(String userEmail);

}
