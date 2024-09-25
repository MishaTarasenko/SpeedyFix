package ukma.speedyfix.service.customer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.CustomerView;
import ukma.speedyfix.merger.CustomerMerger;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.VehicleRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService implements MyService<CustomerEntity, CustomerView, Integer> {

    private final MyValidator<CustomerEntity> validator;
    private final CustomerRepository repository;
    private final VehicleRepository vehicleRepository;
    private final CustomerMerger merger;

    public CustomerResponse getResponseById(Integer id) {
        CustomerEntity entity =  repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found"));
        return buildResponse(entity);
    }

    public List<CustomerResponse> getListResponse(Map<String, Object> criteria) {
        return repository.findAll().stream()
                .map(this::buildResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerEntity getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found"));
    }

    @Override
    public List<CustomerEntity> getList(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public Integer create(CustomerView view) {
        CustomerEntity entity = new CustomerEntity();
        merger.merge(entity, view);
        validator.validForCreate(entity);
        entity = repository.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean update(CustomerView view) {
        CustomerEntity entity = getById(view.getId());
        merger.merge(entity, view);
        validator.validForUpdate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        CustomerEntity entity = getById(id);
        validator.validForDelete(entity);

        List<VehicleEntity> vehicles = vehicleRepository.findAllByOwnerId(id);
        vehicleRepository.deleteAll(vehicles);

        repository.delete(entity);
        return true;
    }

    private CustomerResponse buildResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .user(entity.getUser())
                .build();
    }
}
