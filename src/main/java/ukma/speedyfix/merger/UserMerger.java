package ukma.speedyfix.merger;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.UserEntity;

@Component
public class UserMerger {

    public void merge(UserEntity entity, UserEntity view) {
        if (view.getFirstName() != null) {
            entity.setFirstName(view.getFirstName());
        }
        if (view.getLastName() != null) {
            entity.setLastName(view.getLastName());
        }
        if (view.getEmail() != null) {
            entity.setEmail(view.getEmail());
        }
        if (view.getTelephoneNumber() != null) {
            entity.setTelephoneNumber(view.getTelephoneNumber());
        }
    }
}
