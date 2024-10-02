package ukma.speedyfix.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.type.OperationOrderStatusType;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationOrderResponse {

    private Integer id;
    private OperationOrderStatusType orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<OperationResponse> operations;
    private VehicleResponse vehicle;
    private CustomerResponse customer;
    private Set<EmployeeResponse> employee;
}
