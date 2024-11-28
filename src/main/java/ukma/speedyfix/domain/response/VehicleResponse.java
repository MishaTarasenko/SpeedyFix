package ukma.speedyfix.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
