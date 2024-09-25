package ukma.speedyfix.domain.response;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.entity.CustomerEntity;
import ukma.speedyfix.domain.type.EngineType;
import ukma.speedyfix.domain.type.TransmissionType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Integer id;
    private String brand;
    private String model;
    private int yearOfRelease;
    private EngineType engineType;
    private Double displacement;
    private TransmissionType transmissionType;
    private int wheelRadius;
    private String registrationNumber;
    private CustomerResponse owner;
}
