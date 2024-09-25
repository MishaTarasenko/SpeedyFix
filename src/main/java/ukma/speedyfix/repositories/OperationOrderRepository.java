package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.speedyfix.domain.entity.OperationOrderEntity;

@Repository
public interface OperationOrderRepository extends JpaRepository<OperationOrderEntity, Integer> {
}