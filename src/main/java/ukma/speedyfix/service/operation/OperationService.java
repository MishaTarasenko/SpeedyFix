package ukma.speedyfix.service.operation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.response.EmployeeResponse;
import ukma.speedyfix.domain.response.OperationResponse;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.merger.OperationMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService implements MyService<OperationEntity, OperationView, Integer>, MyOperationService {

    private final MyValidator<OperationEntity> validator;
    private final OperationRepository repository;
    private final EmployeeRepository employeeRepository;
    private final OperationMerger merger;

    public OperationResponse getResponseById(Integer id) {
        OperationEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + id + " not found!"));
        return buildResponse(entity);
    }

    public List<OperationResponse> getListResponse() {
        return repository.findAll().stream()
                .map(this::buildResponse).collect(Collectors.toList());
    }


    @Override
    public OperationEntity getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + id + " not found!"));
    }

    @Override
    public List<OperationEntity> getList(Map<String, Object> criteria) {
        return List.of();
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

    public boolean addEmployees(Integer id, Set<Integer> employeesId) {
        OperationEntity entity = getById(id);
        validator.validForUpdate(entity);
        entity.getEmployees().addAll(employeeRepository.findAllById(employeesId));
        repository.saveAndFlush(entity);
        return true;
    }

    public boolean removeEmployees(Integer id, List<Integer> employeesId) {
        OperationEntity entity = getById(id);
        validator.validForUpdate(entity);
        List<EmployeeEntity> employees = entity.getEmployees();
        employees.removeAll(employeeRepository.findAllById(employeesId));
        entity.setEmployees(employees);
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
    public boolean addEmployeeToOperation(Integer employeeId, Integer operationId) {
        return false;
    }

    private OperationResponse buildResponse(OperationEntity entity) {
        return OperationResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .employees(entity.getEmployees().stream()
                        .map(this::buidEmployeeResponse).collect(Collectors.toList()))
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
