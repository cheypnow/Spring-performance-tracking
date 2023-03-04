package com.xyz.core.aop

import com.xyz.core.monitoring.PerformanceTrackingService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.annotation.PostConstruct

@Aspect
class PerformanceTrackingAdvice constructor(
    private val performanceTrackingService: PerformanceTrackingService
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun postConstruct() {
        log.info("Initialized PerformanceMonitoringAdvice")
    }

    @Around(value = "@annotation(com.xyz.core.aop.PerformanceTracking)", argNames = "jp")
    fun process(jp: ProceedingJoinPoint): Any? {
        return performanceTrackingService.proceedJoinPoint(jp)
    }
}