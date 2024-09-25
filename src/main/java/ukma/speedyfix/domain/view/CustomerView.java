package ukma.speedyfix.domain.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerView {

    private Integer id;
    private Integer userId;
}
