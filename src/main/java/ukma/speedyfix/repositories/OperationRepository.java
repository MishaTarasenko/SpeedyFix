package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.speedyfix.domain.entity.OperationEntity;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Integer> {

    List<OperationEntity> findAllByEmployeeId(Integer employeeId);
}
