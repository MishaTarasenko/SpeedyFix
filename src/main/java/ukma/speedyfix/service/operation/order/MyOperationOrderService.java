package ukma.speedyfix.service.operation.order;

import ukma.speedyfix.domain.type.OperationOrderStatusType;

public interface MyOperationOrderService {

    public boolean changeStatusOfOrder(Integer orderId, OperationOrderStatusType status);

    public boolean operateOperation(Integer operationOrderId, Integer operationId, boolean isAdd);

    public boolean operateEmployee(Integer operationOrderId, Integer employeeId, boolean isAdd);
}
