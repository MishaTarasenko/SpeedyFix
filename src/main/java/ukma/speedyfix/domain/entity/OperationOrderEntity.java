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
        return "This order is for " + vehicle.toString();
    }

    @Override
    public String printCustomer() {
        return "This is order of " + customer.toString();
    }

    @Override
    public String printEmployee() {
        return "This order will be done by " + employee.toString();
    }

    @Override
    public String printOperation() {
        return "This order contains " + operation.toString();
    }
}
