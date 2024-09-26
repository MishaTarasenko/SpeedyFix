package ukma.speedyfix.service.operation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.response.EmployeeResponse;
import ukma.speedyfix.domain.response.OperationResponse;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.merger.OperationMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService implements MyService<OperationEntity, OperationView, Integer>, MyOperationService {

    private final MyValidator<OperationEntity> validator;
    private final OperationRepository repository;
    private final EmployeeRepository employeeRepository;
    private final OperationMerger merger;

    public OperationResponse getResponseById(Integer id) {
        return buildResponse(getById(id));
    }

    public List<OperationResponse> getListResponse() {
        return repository.findAll().stream().map(this::buildResponse).toList();
    }

    @Override
    public OperationEntity getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + id + " not found!"));
    }

    @Override
    public Integer create(OperationView view) {
        OperationEntity entity = new OperationEntity();
        merger.mergeCreate(entity, view);
        validator.validForCreate(entity);
        return repository.save(entity).getId();
    }

    @Override
    public boolean update(OperationView view) {
        OperationEntity entity = getById(view.getId());
        merger.mergeUpdate(entity, view);
        validator.validForUpdate(entity);
        repository.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        OperationEntity entity = getById(id);
        validator.validForDelete(entity);
        repository.delete(entity);
        return false;
    }

    @Override
    public boolean addRemoveEmployeesToOperation(Integer operationId, List<Integer> employeesId, boolean isAdd) {
        OperationEntity entity = getById(operationId);
        validator.validForUpdate(entity);
        List<EmployeeEntity> employees = entity.getEmployees();
        if (isAdd) {
            employees.addAll(employeeRepository.findAllById(employeesId));
        } else {
            employees.removeAll(employeeRepository.findAllById(employeesId));
        }
        entity.setEmployees(employees);
        repository.saveAndFlush(entity);
        return true;
    }

    private OperationResponse buildResponse(OperationEntity entity) {
        return OperationResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .employees(entity.getEmployees().stream()
                        .map(this::buidEmployeeResponse).toList())
                .build();
    }

    private EmployeeResponse buidEmployeeResponse(EmployeeEntity entity) {
        return EmployeeResponse.builder()
                .id(entity.getId())
                .position(entity.getPosition())
                .type(entity.getType())
                .user(entity.getUser())
                .build();
    }
}
