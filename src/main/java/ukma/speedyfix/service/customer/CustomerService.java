package ukma.speedyfix.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements MyService<CustomerEntity, Integer> {

    @Autowired
    private MyValidator<CustomerEntity> validator;

    @Override
    public CustomerEntity getById(Integer id) {
        return null;
    }

    @Override
    public List<CustomerEntity> getList(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public Integer create(CustomerEntity view) {
        return 0;
    }

    @Override
    public boolean update(CustomerEntity view) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
