package ukma.speedyfix.domain.view;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EmployeeView {

    private Integer id;
    private String position;
    private EmployeeType type;
    private Integer userId;
}
