package com.xyz.core.monitoring

import org.aspectj.lang.ProceedingJoinPoint

/**
 * Instance must be unique for each request
 */
interface PerformanceTrackingService {

    fun registerNewRequest(endpointName: String)

    fun proceedJoinPoint(jp: ProceedingJoinPoint): Any?

    fun finishRequest()
}