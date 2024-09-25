package ukma.speedyfix.service.customer;

import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.service.BaseValidator;

@Component
public class CustomerValidator extends BaseValidator<CustomerEntity> {

    public CustomerValidator() {
        super(CustomerEntity.class);
    }
}
