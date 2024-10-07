package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.type.OperationOrderStatusType;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation_order")
public class OperationOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OperationOrderStatusType orderStatus;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.REFRESH)
    private Set<OperationEntity> operations;

    @OneToOne(cascade = CascadeType.REFRESH)
    private VehicleEntity vehicle;

    @OneToOne(cascade = CascadeType.REFRESH)
    private CustomerEntity customer;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @NotEmpty
    @JoinTable(
            name = "operation_order_employee",
            joinColumns = @JoinColumn(name = "operation_order_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<EmployeeEntity> employee;
}
