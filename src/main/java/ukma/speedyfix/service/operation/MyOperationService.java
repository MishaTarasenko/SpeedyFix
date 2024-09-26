package ukma.speedyfix.service.operation;

import ukma.speedyfix.domain.response.OperationResponse;

import java.util.List;

public interface MyOperationService {

    public boolean addRemoveEmployeesToOperation(Integer operationId, List<Integer> employeesId, boolean isAdd);

    public List<OperationResponse> getAllOperationsByEmployeeId(Integer employeeId);
}
