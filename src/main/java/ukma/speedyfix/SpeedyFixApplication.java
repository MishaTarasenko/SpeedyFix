package ukma.speedyfix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ukma.speedyfix.config.StoConfig;

@SpringBootApplication
@EnableConfigurationProperties(StoConfig.class)
public class SpeedyFixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeedyFixApplication.class, args);
    }

}
