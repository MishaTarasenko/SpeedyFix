package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity implements Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "customer")
    private List<VehicleEntity> vehicles;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Override
    public String printListOfVehicles() {
        StringBuilder sb = new StringBuilder();
        sb.append("User with id "+id+": ");
        for (VehicleEntity vehicle : vehicles) {
            sb.append(vehicle.toString());
        }
        return sb.toString();
    }
}
