package com.stackroute.keepnote.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Component
@Aspect
public class LoggingAspect {

	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

/*	@Before("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void loggBeforeControllerMethod(JoinPoint joinpoint) {

		logger.info("Before====================");
		logger.info("Method Name : " + joinpoint.getSignature().getName());
		logger.info("===========================");

	}

	@After("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void loggAfterMethod(JoinPoint joinpoint) {

		logger.debug("After=========================");
		logger.debug("Method Name : " + joinpoint.getSignature().getName());
		logger.debug("=============================");

	}

	@AfterReturning("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void loggAfterReturnControllerMethod(JoinPoint joinpoint) {

		logger.info("After Return====================");
		logger.info("Method Name : " + joinpoint.getSignature().getName());
		logger.info("================================");

	}

	@Around("execution(*c om.stackroute.keepnote.controller.*.*(..))")
	public void loggAroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		logger.info("Before ****** Around advice");
		joinPoint.proceed();
		logger.info("After ****** Around advice");
		logger.info("After Return===================");
		logger.info("Method name : " + joinPoint.getSignature().getName());
		logger.info("================================");

	}

*/
}
