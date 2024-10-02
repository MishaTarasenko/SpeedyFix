package ukma.speedyfix.service.operation.order;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.OperationOrderEntity;
import ukma.speedyfix.service.BaseValidator;

@Component
public class OperationOrderValidator extends BaseValidator<OperationOrderEntity> {

    public OperationOrderValidator() {
        super(OperationOrderEntity.class);
    }
}
