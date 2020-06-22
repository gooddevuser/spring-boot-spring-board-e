package com.springdemo.bootboard.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect // Advice + Pointcut
public class LogAspect {
	
	@Pointcut("within( com.springdemo.bootboard.controller.* )") public void controllerPointcut() {};
	@Pointcut("within( com.springdemo.bootboard.service.* )") public void servicePointcut() {};
	@Pointcut("within( com.springdemo.bootboard..* )") public void logPointcut() {};
	
	//@Before("controllerPointcut()")
	public void doBefore(JoinPoint joinPoint) {		
		System.out.println(String.format("Before %s.%s", 
				joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName()));		
	}
	
	//@After("servicePointcut()")
	public void doAfter(JoinPoint joinPoint) {		
		System.out.println(String.format("After %s.%s", 
				joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName()));		
	}
	
	@Around("logPointcut()")
	public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		long start = System.currentTimeMillis();
		
		Object returnValue = null;
		
		// 실제 메서드 호출
		returnValue = joinPoint.proceed();
		
		long stop = System.currentTimeMillis();
		
		System.out.printf("Execution Lap of %s.%s: %d\n", 
				joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName(),
				(stop - start));
		
		return returnValue;
	}

}

















