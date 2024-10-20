package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.entity.EmployeeEntity;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query("select employee from EmployeeEntity employee where employee.user.email = :email")
    Optional<EmployeeEntity> findByEmail(@Param("email") String email);
}
