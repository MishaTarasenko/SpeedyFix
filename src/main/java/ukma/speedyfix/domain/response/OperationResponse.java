package ukma.speedyfix.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationResponse {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private List<EmployeeResponse> employees;
}
