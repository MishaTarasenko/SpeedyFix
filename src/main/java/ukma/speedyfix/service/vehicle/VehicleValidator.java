package ukma.speedyfix.service.vehicle;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.service.BaseValidator;

@Component
public class VehicleValidator extends BaseValidator<VehicleEntity> {

    public VehicleValidator() {
        super(VehicleEntity.class);
    }
}
