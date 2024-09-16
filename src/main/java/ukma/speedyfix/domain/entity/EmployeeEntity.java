package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.type.EmployeeType;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity implements Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size
    private String position;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Override
    public String printUser() {
        return null;
    }
}
