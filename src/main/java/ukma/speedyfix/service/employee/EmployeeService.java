package ukma.speedyfix.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService implements MyService<EmployeeEntity, Integer> {

    private final MyValidator<EmployeeEntity> validator;

    @Autowired
    public EmployeeService(MyValidator<EmployeeEntity> validator) {
        this.validator = validator;
    }

    @Override
    public EmployeeEntity getById(Integer id) {
        return null;
    }

    @Override
    public List<EmployeeEntity> getList(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public Integer create(EmployeeEntity view) {
        return 0;
    }

    @Override
    public boolean update(EmployeeEntity view) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
