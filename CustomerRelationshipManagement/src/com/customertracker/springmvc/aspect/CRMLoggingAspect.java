package com.customertracker.springmvc.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	private Logger myLogger = Logger.getLogger(getClass().getName());
	@Pointcut("execution(* com.customertracker.springmvc.controller.*.*(..))")
	public void controllerLogging(){}
	
	@Pointcut("execution(* com.customertracker.springmvc.service.*.*(..))")
	public void serviceLogging(){}
	
	@Pointcut("execution(* com.customertracker.springmvc.dao.*.*(..))")
	public void daoLogging(){}
	
	@Pointcut("controllerLogging() || serviceLogging() || daoLogging()")
	public void appFlow(){}
	
	@Before("appFlow()")
	public void before(JoinPoint theJoinPoint){
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("@Before calling method: "+ theMethod);
	
		Object[] args = theJoinPoint.getArgs();
		
		for(Object tempArg : args){
			myLogger.info("argument:" +tempArg);
		}
	}
	
	@AfterReturning(pointcut="appFlow()", returning="theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult){
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("@AfterReturning calling method: "+ theMethod);
		
		myLogger.info("result: "+ theResult);
	}
}
