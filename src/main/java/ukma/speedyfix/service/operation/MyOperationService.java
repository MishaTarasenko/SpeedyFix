package ukma.speedyfix.service.operation;

import java.util.List;

public interface MyOperationService {

    boolean addRemoveEmployeesToOperation(Integer operationId, List<Integer> employeesId, boolean isAdd);
}
