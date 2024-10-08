package ukma.speedyfix.merger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.view.EmployeeView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class EmployeeMerger {
    private final UserRepository repository;

    public void mergeCreate(EmployeeEntity entity, EmployeeView view) {
        if (view.getPosition() != null) {
            entity.setPosition(view.getPosition());
        }
        if (view.getType() != null) {
            entity.setType(view.getType());
        }
        if (view.getUserId() != null) {
           entity.setUser(
                   repository.findById(view.getUserId())
                           .orElseThrow(()->new NoSuchEntityException("User with id: " + view.getUserId() + " not found!"))
           );
        }
    }

    public void mergeUpdate(EmployeeEntity entity, EmployeeView view) {
        if (view.getPosition() != null) {
            entity.setPosition(view.getPosition());
        }
        if (view.getType() != null) {
            entity.setType(view.getType());
        }
    }
}
