package ukma.speedyfix.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "sto")
public class StoConfig {

    private String openingHours;
    private String closingHours;
    private String weekend;
}