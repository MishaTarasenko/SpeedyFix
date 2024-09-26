package ukma.speedyfix.domain.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationView {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Set<Integer> employeeIds;
}
