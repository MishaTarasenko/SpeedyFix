package ukma.speedyfix.config;

import jakarta.validation.Validator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ukma.speedyfix.cache.CustomCacheManager;

@Configuration
@EnableScheduling
@EnableCaching
@ComponentScan(value = "ukma.speedyfix")
public class AppConfiguration {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public CacheManager cacheManager() {
        return new CustomCacheManager();
    }
}
