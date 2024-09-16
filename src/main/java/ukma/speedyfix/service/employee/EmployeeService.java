package ukma.speedyfix.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.service.IValidator;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeService(IValidator<EmployeeEntity> validator) {}
}
