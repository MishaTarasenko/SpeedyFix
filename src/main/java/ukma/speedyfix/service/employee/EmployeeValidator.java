package ukma.speedyfix.service.employee;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.security.SecurityContextAccessor;
import ukma.speedyfix.service.BaseValidator;

@Component
public class EmployeeValidator extends BaseValidator<EmployeeEntity> {

    public EmployeeValidator() {
        super(EmployeeEntity.class);
    }

    @Override
    public void validForCreate(EmployeeEntity entity) {
        super.validForCreate(entity);

        if (!hasPermissionToEdit(entity.getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForDelete(EmployeeEntity entity) {
        super.validForDelete(entity);

        if (!hasPermissionToEdit(entity.getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForUpdate(EmployeeEntity entity) {
        super.validForUpdate(entity);

        if (!hasPermissionToEdit(entity.getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    private boolean hasPermissionToEdit(String email) {
        return SecurityContextAccessor.getRole().equals("ROLE_ADMIN");
    }
}
