package ukma.speedyfix.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.type.EmployeeType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Integer id;
    private String position;
    private EmployeeType type;
    private UserEntity user;
}
