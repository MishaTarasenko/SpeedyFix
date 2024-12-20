package ukma.speedyfix.service.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.response.VehicleResponse;
import ukma.speedyfix.domain.view.VehicleView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.merger.VehicleMerger;
import ukma.speedyfix.repositories.VehicleRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;
import ukma.speedyfix.service.customer.CustomerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService implements MyService<VehicleEntity, VehicleView,  Integer> {

    private final MyValidator<VehicleEntity> validator;
    private final VehicleRepository repository;
    private final CustomerService customerService;
    private final VehicleMerger merger;

    public VehicleResponse getResponseById(Integer id) {
        return buildResponse(getById(id));
    }

    public List<VehicleResponse> getListResponse() {
        List<VehicleEntity> response = repository.findAll();

        for (VehicleEntity vehicle : response) {
            validator.validForView(vehicle);
        }

        return response.stream().map(this::buildResponse).toList();
    }

    @Override
    public VehicleEntity getById(Integer id) {
        VehicleEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Vehicle with id: " + id + " not found!"));
        validator.validForView(entity);
        return entity;
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
        validator.validForUpdate(entity);
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
        List<VehicleEntity> response = repository.findAllByOwner(customerService.getById(customerId));

        for (VehicleEntity vehicle : response) {
            validator.validForView(vehicle);
        }

        return response.stream().map(this::buildResponse).toList();
    }

    public VehicleResponse buildResponse(VehicleEntity entity) {
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
