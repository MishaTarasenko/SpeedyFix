package ukma.speedyfix.domain.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {

    private String startTime;
    private String endTime;
    private String weekend;
}
