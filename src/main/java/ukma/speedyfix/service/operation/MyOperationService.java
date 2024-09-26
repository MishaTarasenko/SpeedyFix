package ukma.speedyfix.service.operation;

import java.util.List;

public interface MyOperationService {

    public boolean addRemoveEmployeesToOperation(Integer operationId, List<Integer> employeesId, boolean isAdd);
}
