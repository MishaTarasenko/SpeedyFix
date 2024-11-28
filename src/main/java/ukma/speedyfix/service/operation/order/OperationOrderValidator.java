package ukma.speedyfix.service.operation.order;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.OperationOrderEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.security.SecurityContextAccessor;
import ukma.speedyfix.service.BaseValidator;

import java.time.LocalDate;

@Component
public class OperationOrderValidator extends BaseValidator<OperationOrderEntity> {

    public OperationOrderValidator() {
        super(OperationOrderEntity.class);
    }

    @Override
    public void validForCreate(OperationOrderEntity entity) {
        super.validForCreate(entity);

        if (entity.getStartDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Start date must be from today or later");
        }
    }

    @Override
    public void validForDelete(OperationOrderEntity entity) {
        super.validForDelete(entity);

        if (!hasPermissionToEdit(entity.getCustomer().getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForUpdate(OperationOrderEntity entity) {
        super.validForUpdate(entity);

        if (!hasPermissionToEdit(entity.getCustomer().getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForView(OperationOrderEntity entity) {
        super.validForView(entity);

        if (!hasPermissionToView(entity.getCustomer().getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    private boolean hasPermissionToView(String email) {
        if (SecurityContextAccessor.getBehalfOnEmail().equals(email)) {
            return true;
        }

        return SecurityContextAccessor.getRole().equals("ROLE_ADMIN")
                || SecurityContextAccessor.getRole().equals("ROLE_MECHANIC");
    }

    private boolean hasPermissionToEdit(String email) {
        return SecurityContextAccessor.getBehalfOnEmail().equals(email)
                || SecurityContextAccessor.getRole().equals("ROLE_ADMIN");
    }
}
