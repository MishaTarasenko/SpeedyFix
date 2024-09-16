package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationOrderEntity implements OperationOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private OperationEntity operation;

    @OneToOne
    private VehicleEntity vehicle;

    @OneToOne
    private CustomerEntity customer;

    @OneToOne
    private EmployeeEntity employee;

    @Override
    public String printVehicle() {
        return null;
    }

    @Override
    public String printCustomer() {
        return null;
    }

    @Override
    public String printEmployee() {
        return null;
    }

    @Override
    public String printOperation() {
        return null;
    }
}
