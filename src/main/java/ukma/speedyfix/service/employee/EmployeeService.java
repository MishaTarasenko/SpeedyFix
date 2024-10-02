package ukma.speedyfix.service.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.response.EmployeeResponse;
import ukma.speedyfix.domain.view.EmployeeView;
import ukma.speedyfix.exception.NoSuchEntityException;
import ukma.speedyfix.merger.EmployeeMerger;
import ukma.speedyfix.repositories.EmployeeRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements MyService<EmployeeEntity, EmployeeView, Integer> {

    private final MyValidator<EmployeeEntity> validator;
    private final EmployeeRepository repository;
    private final EmployeeMerger merger;

    public EmployeeResponse getResponseById(Integer id) {
        return buildResponse(getById(id));
    }

    public List<EmployeeResponse> getList() {
        return repository.findAll().stream().map(this::buildResponse).toList();
    }

    @Override
    public EmployeeEntity getById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException("Employee with id: " + id + " not found!"));
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

    private EmployeeResponse buildResponse(EmployeeEntity entity) {
        return EmployeeResponse.builder()
                .id(entity.getId())
                .position(entity.getPosition())
                .type(entity.getType())
                .user(entity.getUser())
                .build();
    }
}
