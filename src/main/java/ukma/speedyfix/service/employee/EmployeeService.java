package ukma.speedyfix.service.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.EmployeeView;
import ukma.speedyfix.merger.EmployeeMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService implements MyService<EmployeeEntity, EmployeeView, Integer> {

    private final MyValidator<EmployeeEntity> validator;
    private final EmployeeRepository repository;
    private final EmployeeMerger merger;

    @Override
    public EmployeeEntity getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee with id: "+id+" not found!"));
    }

    @Override
    public List<EmployeeEntity> getList(Map<String, Object> criteria) {
        return repository.findAll();
    }

    @Override
    public Integer create(EmployeeView view) {
        EmployeeEntity entity = new EmployeeEntity();
        merger.mergeCreate(entity, view);
        validator.validForCreate(entity);
        entity = repository.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean update(EmployeeView view) {
        EmployeeEntity entity = getById(view.getId());
        merger.mergeUpdate(entity, view);
        validator.validForCreate(entity);
        repository.saveAndFlush(entity);
        return true;

    }

    @Override
    public boolean delete(Integer id) {
        EmployeeEntity entity = getById(id);
        validator.validForDelete(entity);
        repository.delete(entity);
        return true;
    }
}
