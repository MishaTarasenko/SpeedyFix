package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "operation_name")
    private String name;

    @NotBlank
    @Column(name = "operation_description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "operation_employee",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<EmployeeEntity> employees = new HashSet<>();
}
