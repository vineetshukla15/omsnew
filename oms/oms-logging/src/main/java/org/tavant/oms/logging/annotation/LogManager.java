package org.tavant.oms.logging.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogManager {

       @Pointcut("execution(* com.oms.service.*.*(..))")
       public void auditLog() {
       }

       @Pointcut("execution(* com.oms.controller.*.*(..))")
       public void performanceLog(){
       }
       
       @Pointcut("execution(* com.oms.service.*.*(..))")
       public void afterLog(){
       }
       
       @Pointcut("execution(* com.oms.service.*.*(..))")
       public void logAfterReturning(){
       }
}
