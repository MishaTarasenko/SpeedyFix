package ukma.speedyfix.service.operation.order;

import ukma.speedyfix.domain.type.OperationOrderStatusType;

public interface MyOperationOrderService {

    boolean changeStatusOfOrder(Integer orderId, OperationOrderStatusType status);

    boolean operateOperation(Integer operationOrderId, Integer operationId, boolean isAdd);

    boolean operateEmployee(Integer operationOrderId, Integer employeeId, boolean isAdd);
}
