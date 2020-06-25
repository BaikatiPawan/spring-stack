package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */

@Component
@Aspect
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Category controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


	@Before("execution(* com.stackroute.keepnote.controller.*.get*())")
	public void before(JoinPoint joinPoint) {
		logger.info("entering Controller method" + joinPoint.getSignature().getName());
	}

	@After("execution(* com.stackroute.keepnote.controller.*.get*())")
	public void after(JoinPoint joinPoint) {
		logger.info("entering Controller method" + joinPoint.getSignature().getName());
	}

	@AfterReturning("execution(* com.stackroute.keepnote.controller.*.get*())")
	public void afterRet(JoinPoint joinPoint) {
		logger.info("entering Controller method" + joinPoint.getSignature().getName());
	}

	@AfterThrowing("execution(* com.stackroute.keepnote.controller.*.get*())")
	public void afterThrow(JoinPoint joinPoint) {
		logger.info("entering Controller method" + joinPoint.getSignature().getName());
	}


}
