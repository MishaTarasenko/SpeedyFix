package ukma.speedyfix.domain.view;


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
public class OperationOrderView {

    private Integer id;
    private OperationOrderStatusType orderStatus;
    private LocalDate endDate;
    private Set<Integer> operationIds;
    private Integer vehicleId;
    private Integer customerId;
    private Set<Integer> employeeIds;
}
