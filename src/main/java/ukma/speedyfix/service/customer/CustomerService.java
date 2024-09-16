package ukma.speedyfix.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.service.IValidator;

@Service
public class CustomerService {

    @Autowired
    private IValidator<CustomerEntity> validator;
}
