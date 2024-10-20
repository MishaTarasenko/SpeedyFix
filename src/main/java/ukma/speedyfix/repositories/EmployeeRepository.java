package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.speedyfix.domain.entity.EmployeeEntity;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    Optional<EmployeeEntity> findByEmail(String email);
}
