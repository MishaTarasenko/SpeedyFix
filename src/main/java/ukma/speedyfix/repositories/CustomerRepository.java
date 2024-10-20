package ukma.speedyfix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ukma.speedyfix.domain.entity.CustomerEntity;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Query("select customer from CustomerEntity customer where customer.user.email = :email")
    Optional<CustomerEntity> findByEmail(@Param("email") String email);
}
