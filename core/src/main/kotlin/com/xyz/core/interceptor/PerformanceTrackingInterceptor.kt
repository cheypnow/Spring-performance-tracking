package com.xyz.core.interceptor

import com.xyz.core.monitoring.PerformanceTrackingService
import org.springframework.ui.ModelMap
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

class PerformanceTrackingInterceptor constructor(
    private val performanceTrackingService: PerformanceTrackingService
) : SpringRequestInterceptor {

    override fun preHandle(request: WebRequest) {
        val endpoint = (request as ServletWebRequest).request.requestURI
        performanceTrackingService.registerNewRequest(endpoint)
    }

    override fun postHandle(request: WebRequest, model: ModelMap?) {
        performanceTrackingService.finishRequest()
    }

    override fun afterCompletion(request: WebRequest, ex: Exception?) {}
}