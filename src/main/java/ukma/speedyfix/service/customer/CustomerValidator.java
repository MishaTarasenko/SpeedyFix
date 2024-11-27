package ukma.speedyfix.service.customer;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.security.SecurityContextAccessor;
import ukma.speedyfix.service.BaseValidator;

@Component
public class CustomerValidator extends BaseValidator<CustomerEntity> {

    public CustomerValidator() {
        super(CustomerEntity.class);
    }

    @Override
    public void validForDelete(CustomerEntity entity) {
        super.validForDelete(entity);

        if (!hasPermissionToEdit(entity.getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForUpdate(CustomerEntity entity) {
        super.validForUpdate(entity);

        if (!hasPermissionToEdit(entity.getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForView(CustomerEntity entity) {
        super.validForView(entity);

        if (!hasPermissionToView(entity.getUser().getEmail())) {
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
        return SecurityContextAccessor.getBehalfOnEmail().equals(email);
    }
}
