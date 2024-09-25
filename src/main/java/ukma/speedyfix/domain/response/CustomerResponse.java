package ukma.speedyfix.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.speedyfix.domain.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Integer id;
    private UserEntity user;
}
