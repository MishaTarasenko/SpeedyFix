package ukma.speedyfix.service.operation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.merger.OperationMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OperationService implements MyService<OperationEntity, OperationView, Integer>, MyOperationService {

    private final MyValidator<OperationEntity> validator;
    private final OperationRepository repository;
    private final EmployeeRepository employeeRepository;
    private final OperationMerger merger;

    @Transactional
    @Override
    public OperationEntity getById(Integer id) {
        OperationEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + id + " not found!"));
        repository.findAll().forEach(System.out::println);
        return entity;
    }

    @Override
    public List<OperationEntity> getList(Map<String, Object> criteria) {
        return repository.findAll();
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
        Set<EmployeeEntity> employees = entity.getEmployees();
        employeeRepository.findAll().forEach(System.out::println);
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
}
