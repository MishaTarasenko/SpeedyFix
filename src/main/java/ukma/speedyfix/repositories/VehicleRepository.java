package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.speedyfix.domain.entity.VehicleEntity;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {

    List<VehicleEntity> findAllByOwnerId(Integer ownerId);
}
