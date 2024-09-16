package ukma.speedyfix.service.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.service.IValidator;

@Service
public class VehicleService {

    @Autowired
    public VehicleService(IValidator<VehicleEntity> validator) {}
}
