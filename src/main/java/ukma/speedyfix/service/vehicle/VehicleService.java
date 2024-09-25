package ukma.speedyfix.service.vehicle;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.merger.VehicleMerger;
import ukma.speedyfix.repositories.VehicleRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VehicleService implements MyService<VehicleEntity, VehicleView,  Integer> {

    private final MyValidator<VehicleEntity> validator;
    private final VehicleRepository repository;
    private final VehicleMerger merger;

    @Override
    public VehicleEntity getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vehicle with id: "+id+" not found!"));
    }

    @Override
    public List<VehicleEntity> getList(Map<String, Object> criteria) {
        return repository.findAll();
    }

    @Override
    public Integer create(VehicleView view) {
        VehicleEntity entity = new VehicleEntity();
        merger.mergeCreate(entity, view);
        validator.validForCreate(entity);
        entity = repository.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean update(VehicleView view) {
        VehicleEntity entity = getById(view.getId());
        merger.mergeUpdate(entity, view);
        validator.validForCreate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        VehicleEntity entity = getById(id);
        validator.validForDelete(entity);
        repository.delete(entity);
        return true;
    }
}
