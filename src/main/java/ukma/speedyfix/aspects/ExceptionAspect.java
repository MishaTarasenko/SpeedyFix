package ukma.speedyfix.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

    private static final Logger log = LogManager.getLogger(ExceptionAspect.class);

    @AfterThrowing(pointcut = "execution(* ukma.speedyfix.service..*.*(..))", throwing = "ex")
    public void handleExceptions(Exception ex) {
        logErrorBasedOnExceptionType(ex);
    }

    private void logErrorBasedOnExceptionType(Exception ex) {
        log.error("Aspect exception: {}", ex.getMessage());
    }
}
