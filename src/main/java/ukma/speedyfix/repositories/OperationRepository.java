package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.speedyfix.domain.entity.OperationEntity;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Integer> {
}
