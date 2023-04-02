package brd.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Advice{
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Before("execution(* brd.service.ServiceClass.*(..))")
    public void beforeRegister(JoinPoint j) {
        logger.info(j.getSignature());
    }
}
