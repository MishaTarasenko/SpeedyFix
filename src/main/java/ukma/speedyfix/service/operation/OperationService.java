package ukma.speedyfix.service.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.response.CustomerResponse;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.merger.OperationMerger;
import ukma.speedyfix.repositories.OperationRepository;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationService implements MyService<OperationEntity, OperationView, Integer>, MyOperationService {

    private final MyValidator<OperationEntity> validator;
    private final OperationRepository repository;
    private final OperationMerger merger;

    @Override
    public OperationEntity getById(Integer id) {
        return null;
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
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean addEmployeeToOperation(Integer employeeId, Integer operationId) {
        return false;
    }
}
