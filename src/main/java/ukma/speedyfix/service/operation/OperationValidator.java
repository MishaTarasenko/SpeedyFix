package ukma.speedyfix.service.operation;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.security.SecurityContextAccessor;
import ukma.speedyfix.service.BaseValidator;

@Component
public class OperationValidator extends BaseValidator<OperationEntity> {

    public OperationValidator() {
        super(OperationEntity.class);
    }

    @Override
    public void validForCreate(OperationEntity entity) {
        super.validForCreate(entity);

        if (!hasPermissionToEdit()) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForDelete(OperationEntity entity) {
        super.validForDelete(entity);

        if (!hasPermissionToEdit()) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForUpdate(OperationEntity entity) {
        super.validForUpdate(entity);

        if (!hasPermissionToEdit()) {
            throw new ValidationException("You do not have permission");
        }
    }

    private boolean hasPermissionToEdit() {
        return SecurityContextAccessor.getRole().equals("ROLE_ADMIN");
    }
}
