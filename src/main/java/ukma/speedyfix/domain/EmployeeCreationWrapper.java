package ukma.speedyfix.domain;

import lombok.Data;
import ukma.speedyfix.domain.entity.UserEntity;
import ukma.speedyfix.domain.view.EmployeeView;

@Data
public class EmployeeCreationWrapper {

    private EmployeeView employee;
    private UserEntity user;
}