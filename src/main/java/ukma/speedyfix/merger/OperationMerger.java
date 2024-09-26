package ukma.speedyfix.merger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ukma.speedyfix.domain.entity.EmployeeEntity;
import ukma.speedyfix.domain.entity.OperationEntity;
import ukma.speedyfix.domain.view.OperationView;
import ukma.speedyfix.repositories.EmployeeRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OperationMerger {

    private final EmployeeRepository employeeRepository;

    public void mergeCreate(OperationEntity entity, OperationView view) {
        if (view.getName() != null) {
            entity.setName(view.getName());
        }
        if (view.getDescription() != null) {
            entity.setDescription(view.getDescription());
        }
        if (view.getPrice() != null) {
            entity.setPrice(view.getPrice());
        }
        if (view.getEmployeeIds() != null && !view.getEmployeeIds().isEmpty()) {
            entity.setEmployees(
                    new HashSet<>(employeeRepository.findAllById(view.getEmployeeIds()))
            );
        }
    }

    public void mergeUpdate(OperationEntity entity, OperationView view) {
        if (view.getName() != null) {
            entity.setName(view.getName());
        }
        if (view.getDescription() != null) {
            entity.setDescription(view.getDescription());
        }
        if (view.getPrice() != null) {
            entity.setPrice(view.getPrice());
        }
    }

}
