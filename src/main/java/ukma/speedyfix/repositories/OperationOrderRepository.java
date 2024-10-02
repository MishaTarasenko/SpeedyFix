package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.speedyfix.domain.entity.OperationOrderEntity;

import java.util.List;

@Repository
public interface OperationOrderRepository extends JpaRepository<OperationOrderEntity, Integer> {

    List<OperationOrderEntity> findAllByCustomerId(Integer customerId);
    List<OperationOrderEntity> findAllByEmployeeId(Integer employeeId);
}