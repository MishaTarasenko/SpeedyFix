package ukma.speedyfix.service.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;
import ukma.speedyfix.service.user.MyAuthService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleService implements MyService<VehicleEntity, VehicleView,  Integer> {

    private MyValidator<VehicleEntity> validator;

    @Autowired
    public VehicleService(MyValidator<VehicleEntity> validator) {
        this.validator = validator;
    }

    @Override
    public VehicleEntity getById(Integer id) {
        return null;
    }

    @Override
    public List<VehicleEntity> getList(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public Integer create(VehicleView view) {
        return 0;
    }

    @Override
    public boolean update(VehicleView view) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
