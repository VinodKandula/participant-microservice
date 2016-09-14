package com.amhzing.participant.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LogExecutionTimeInterceptor {

    private final GaugeService gaugeService;

    @Autowired
    public LogExecutionTimeInterceptor(final GaugeService gaugeService) {
        this.gaugeService = gaugeService;
    }

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() { }

    @Around("anyPublicOperation() && @annotation(com.amhzing.participant.annotation.LogExecutionTime)")
    public Object logExecutionTaken(final ProceedingJoinPoint aProceedingJoinPoint) throws Throwable
    {
        final String nameOfClass = aProceedingJoinPoint.getTarget().toString();
        final Logger logger = LoggerFactory.getLogger(nameOfClass);

        final String shortDescr = aProceedingJoinPoint.toShortString();
        final String nameOfMethod = aProceedingJoinPoint.getSignature().getName();

        final StopWatch sw = new StopWatch();

        // Start the stopwatch
        sw.start(nameOfMethod);

        // Invoke method
        final Object retVal = aProceedingJoinPoint.proceed();

        // Stop the stopwatch
        sw.stop();

        final Long totalTimeMillis = new Long(sw.getTotalTimeMillis());
        final String metricName = "participant." + StringUtils.lowerCase(nameOfMethod) + ".response.time";

        gaugeService.submit(metricName, totalTimeMillis.doubleValue());

        logger.info("{} took {}ms", new Object[] {shortDescr, totalTimeMillis});

        return retVal;
    }
}
