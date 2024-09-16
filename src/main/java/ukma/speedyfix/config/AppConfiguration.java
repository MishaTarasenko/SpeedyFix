package ukma.speedyfix.config;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan(value = "ukma.speedyfix")
public class AppConfiguration {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
