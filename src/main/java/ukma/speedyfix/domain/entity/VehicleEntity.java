package ukma.speedyfix.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.type.EngineType;
import ukma.speedyfix.domain.type.TransmissionType;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "brand")
    private String brand;

    @NotBlank
    @Size(max = 100)
    @Column(name = "model")
    private String model;

    @Min(value = 1975)
    @Column(name = "yearOfRelease")
    private int yearOfRelease;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type")
    private EngineType engineType;

    @Column(name = "displacement")
    private Double displacement;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    private TransmissionType transmissionType;

    @Column(name = "wheel_radius")
    private int wheelRadius;

    @NotBlank
    @Column(name = "registration_number")
    private String registrationNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private CustomerEntity owner;
}
