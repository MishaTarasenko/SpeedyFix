package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.type.OperationOrderStatusType;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationOrderStatusType orderStatus;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @OneToOne
    private OperationEntity operation;

    @OneToOne
    private VehicleEntity vehicle;

    @OneToOne
    private CustomerEntity customer;

    @OneToOne
    private EmployeeEntity employee;
}
