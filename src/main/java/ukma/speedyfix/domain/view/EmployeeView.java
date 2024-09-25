package ukma.speedyfix.domain.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.type.EmployeeType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeView {

    private Integer id;
    private String position;
    private EmployeeType type;
    private Integer userId;
}
