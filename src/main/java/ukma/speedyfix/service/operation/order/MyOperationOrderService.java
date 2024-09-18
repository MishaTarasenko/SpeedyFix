package ukma.speedyfix.service.operation.order;

import ukma.speedyfix.domain.type.OperationOrderStatusType;

public interface MyOperationOrderService {

    public boolean changeStatusOfOrder(Integer orderId, OperationOrderStatusType status);
}
