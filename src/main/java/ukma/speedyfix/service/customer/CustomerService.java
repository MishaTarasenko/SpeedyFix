package ukma.speedyfix.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.entity.VehicleEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.CustomerView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.merger.CustomerMerger;
import ukma.speedyfix.repositories.CustomerRepository;
import ukma.speedyfix.repositories.UserRepository;
import ukma.speedyfix.repositories.VehicleRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;
import ukma.speedyfix.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements MyService<CustomerEntity, CustomerView, Integer> {

    private final MyValidator<CustomerEntity> validator;
    private final CustomerRepository repository;
    private final UserService userService;
    private final VehicleRepository vehicleRepository;
    private final CustomerMerger merger;
    private final UserRepository userRepository;

    public CustomerResponse getResponseById(Integer id) {
        return buildResponse(getById(id));
    }

    public List<CustomerResponse> getListResponse() {
        return repository.findAll().stream()
                .map(this::buildResponse).toList();
    }

    @Override
    public CustomerEntity getById(Integer id) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Customer with id: " + id + " not found"));
        validator.validForView(entity);
        return entity;
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
        return false;
    }

    public boolean update(UserEntity view) {
        return userService.update(view);
    }

    @Override
    public boolean delete(Integer id) {
        CustomerEntity entity = getById(id);
        Integer userId = entity.getUser().getId();
        validator.validForDelete(entity);
        List<VehicleEntity> vehicles = vehicleRepository.findAllByOwner(entity);
        vehicleRepository.deleteAll(vehicles);
        repository.delete(entity);
        userService.delete(userId);
        return true;
    }

    public CustomerResponse buildResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .user(entity.getUser())
                .build();
    }
}
