package org.tavant.oms.logging.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.tavant.oms.logging.annotation.Loggable;

@Service
@Aspect
public class LogInterceptor {
    @Before(value = "org.tavant.oms.logging.annotation.LogManager.auditLog()"
            + "&& target(bean) "
            + "&& @annotation(org.tavant.oms.logging.annotation.Loggable)"
            + "&& @annotation(logme)", argNames = "bean,logme")
    public void log(JoinPoint jp, Object bean, Loggable logme) {
    	Logger logger = LoggerFactory.getLogger(bean.getClass());
    	logger.info(String.format("Log Message: %s", logme.message()));
    	logger.info(String.format("Bean Called: %s", bean.getClass()
                .getName()));
    	logger.info(String.format("Method Called: %s", jp.getSignature()
                .getName()));

    }

/*    @Around(value = "org.tavant.oms.logging.annotation.LogManager.performanceLog()"
            + "&& target(bean) "
            + "&& @annotation(org.tavant.oms.logging.annotation.Loggable)"
            + "&& @annotation(logme)", argNames = "bean,logme")
    public Object performanceLog(ProceedingJoinPoint joinPoint, Object bean, Loggable logme) throws Throwable {
    	Object retVal = joinPoint.proceed();
    	StopWatch stopWatch = new StopWatch();
        Logger logger = LoggerFactory.getLogger(bean.getClass());
        stopWatch.start();
        joinPoint.proceed();
        
        StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        // append args
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }

        logMessage.append(")");
        logMessage.append(" execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");
        logger.info(logMessage.toString());
        stopWatch.stop();
        return retVal;
    }

    @After(value = "org.tavant.oms.logging.annotation.LogManager.afterLog()"
            + "&& target(bean) "
            + "&& @annotation(org.tavant.oms.logging.annotation.Loggable)"
            + "&& @annotation(logme)", argNames = "bean,logme")
	public void logAfter(JoinPoint joinPoint, Object bean, Loggable logme) {
    	Logger logger = LoggerFactory.getLogger(bean.getClass());
		logger.info("End of "+joinPoint.getSignature().getName()+"() mathod execution");
    }
    
	@AfterReturning(value = "org.tavant.oms.logging.annotation.LogManager.logAfterReturning()"
            + "&& target(bean) "
            + "&& @annotation(org.tavant.oms.logging.annotation.Loggable)"
            + "&& @annotation(logme)", argNames = "bean", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object bean, Loggable logme,Object result) {
		Logger logger = LoggerFactory.getLogger(bean.getClass());
		if(logger.isDebugEnabled()){
			logger.info("medthod  " + joinPoint.getSignature().getName()+"() log After Returning");
			logger.info("calling Method returned value is : " + result);

		}
	}*/
/*	@AfterThrowing(pointcut = "execution(* com.tavant.media.core.*.*.*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint,Object bean, Throwable error) {
		//logger.info("I am running *************************************");
		Logger logger = LoggerFactory.getLogger(bean.getClass());
		logger.error("calling medthod  " + joinPoint.getSignature().getName()+"() is thowing some exception");
		logger.error("Exception : " + error);


	}*/
}
