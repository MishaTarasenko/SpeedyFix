package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.speedyfix.domain.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
}
