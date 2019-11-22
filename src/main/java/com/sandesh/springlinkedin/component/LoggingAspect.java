package com.sandesh.springlinkedin.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	
	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	//using annotation for the creation of pointcut
	@Pointcut("@annotation(Loggable)")
	public void executeLogging() {}
	
	//using within for the creation of pointcut
	@Pointcut("within(com.sandesh.springlinkedin.component.Student)")
	public void executeWithin() {}
	
	//using execution for the creation of pointcut
	@Pointcut("execution(* getName(..))")
	public void executeLoggingNormal() {}
	
	//using target for the creation of pointcut
	@Pointcut("target(com.sandesh.springlinkedin.component.Student)")
	public void executeLoggingTarget() {}
	
	@Before("executeLoggingTarget()")
	public void beforeLoggable(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		logger.warn("(Before) Methods annotated with Loggable executing for method: " + methodName);
	}
	
	// Similar to this there is AfterThrowing and After annotations, After execute in both cases
	@AfterReturning(pointcut = "executeLogging()", returning = "returnValue")
	public Object afterLoggable(JoinPoint joinPoint, Object returnValue) {
		String methodName = joinPoint.getSignature().getName();
		logger.warn("(After Returning) Methods annotated with Loggable executing for method: " + methodName);
		logger.warn("String value currently: " + returnValue.toString() + ", Instance of String: " + (returnValue instanceof String));
		
		// value modified is not getting returned
		if (returnValue instanceof String) {
			String modified = (String) returnValue;
			modified = "Modified Value";
			return modified;
		}
		// returned value is still not changed
		return returnValue;
	}
	
	// Be aware while using around as it can modify the returned value intercepting in between
	// Around advice
	@Around("executeLogging()")
	public Object aroundLoggable(ProceedingJoinPoint pJointPoint) throws Throwable {
		String methodName = pJointPoint.getSignature().getName();
		logger.warn("(Around + Before) Methods annotated with Loggable executing for method: " + methodName);
		Object returned = pJointPoint.proceed();
		System.out.println("(Around) String value is previously: " + returned.toString());
		if (returned instanceof String) {
			String newValue = returned.toString();
			newValue = "Modified Name";
			System.out.println("(Around) String value now: " + newValue);
			return newValue;
		}
		return returned;
	}
}
