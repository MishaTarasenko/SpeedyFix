package ukma.speedyfix.service.operation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.entity.OperationOrderEntity;
import ukma.speedyfix.domain.type.OperationOrderStatusType;
import ukma.speedyfix.domain.view.OperationOrderView;
import ukma.speedyfix.service.MyService;
import ukma.speedyfix.service.MyValidator;

import java.util.List;
import java.util.Map;

@Service
public class OperationOrderService implements MyService<OperationOrderEntity, OperationOrderView, Integer>, MyOperationOrderService {

    private final MyValidator<OperationEntity> validator;

    @Autowired
    public OperationOrderService(MyValidator<OperationEntity> validator) {
        this.validator = validator;
    }

    @Override
    public OperationOrderEntity getById(Integer id) {
        return null;
    }

    public List<OperationOrderEntity> getList(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public Integer create(OperationOrderView view) {
        return 0;
    }

    @Override
    public boolean update(OperationOrderView view) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean changeStatusOfOrder(Integer orderId, OperationOrderStatusType status) {
        return true;
    }
}
