package ukma.speedyfix.service.vehicle;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.response.VehicleResponse;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.merger.VehicleMerger;
import ukma.speedyfix.repositories.VehicleRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService implements MyService<VehicleEntity, VehicleView,  Integer> {

    private final MyValidator<VehicleEntity> validator;
    private final VehicleRepository repository;
    private final VehicleMerger merger;

    public VehicleResponse getResponseById(Integer id) {
        VehicleEntity entity =  repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with id: "+id+" not found!"));
        return buildResponse(entity);
    }

    public List<VehicleResponse> getListResponse() {
        return repository.findAll().stream()
                .map(this::buildResponse).collect(Collectors.toList());
    }

    @Override
    public VehicleEntity getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with id: " + id + " not found"));
    }

    @Override
    public List<VehicleEntity> getList(Map<String, Object> criteria) {
        return List.of();
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

    public List<VehicleResponse> getVehiclesByCustomerId(Integer customerId) {
        return repository.findAllByOwnerId(customerId).stream()
                .map(this::buildResponse).collect(Collectors.toList());
    }

    private VehicleResponse buildResponse(VehicleEntity entity) {
        return VehicleResponse.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .yearOfRelease(entity.getYearOfRelease())
                .engineType(entity.getEngineType())
                .displacement(entity.getDisplacement())
                .transmissionType(entity.getTransmissionType())
                .wheelRadius(entity.getWheelRadius())
                .registrationNumber(entity.getRegistrationNumber())
                .owner(new CustomerResponse(entity.getOwner().getId(), entity.getOwner().getUser()))
                .build();
    }
}
