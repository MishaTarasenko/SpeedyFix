package ukma.speedyfix.service.operation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.service.IValidator;

@Service
public class OperationOrderService {

    @Autowired
    public OperationOrderService(IValidator<OperationEntity> validator) {}
}
