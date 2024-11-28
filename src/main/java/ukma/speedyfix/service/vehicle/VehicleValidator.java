package ukma.speedyfix.service.vehicle;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.exception.ValidationException;
import ukma.speedyfix.security.SecurityContextAccessor;
import ukma.speedyfix.service.BaseValidator;

@Component
public class VehicleValidator extends BaseValidator<VehicleEntity> {

    public VehicleValidator() {
        super(VehicleEntity.class);
    }

    @Override
    public void validForDelete(VehicleEntity entity) {
        super.validForDelete(entity);

        if (!hasPermissionToEdit(entity.getOwner().getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForUpdate(VehicleEntity entity) {
        super.validForUpdate(entity);

        if (!hasPermissionToEdit(entity.getOwner().getUser().getEmail())) {
            throw new ValidationException("You do not have permission");
        }
    }

    @Override
    public void validForView(VehicleEntity entity) {
        if (!hasPermissionToView(entity.getOwner().getUser().getEmail())) {
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
