package ukma.speedyfix.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.service.IValidator;

@Service
public class OperationService {

    @Autowired
    public OperationService(IValidator<OperationEntity> validator) {}
}