package com.xyz.core

import com.xyz.core.aop.PerformanceTrackingAdvice
import com.xyz.core.interceptor.PerformanceTrackingInterceptor
import com.xyz.core.monitoring.DefaultPerformanceTrackingService
import com.xyz.core.monitoring.PerformanceTrackingService
import com.xyz.core.output.TracingDataOutputListenerChain
import com.xyz.core.output.TrackingDataOutputListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.context.request.WebRequestInterceptor

@Configuration
@Import(value = [InterceptorConfig::class])
class SpringPerformanceTrackingAutoConfiguration {

    @Bean
    @RequestScope
    fun getPerformanceMonitoringService(
        @Autowired tracingDataOutputListenerChain: TracingDataOutputListenerChain
    ): PerformanceTrackingService {
        return DefaultPerformanceTrackingService(tracingDataOutputListenerChain)
    }

    @Bean
    fun getPerformanceMonitoringInterceptor(
        @Autowired performanceTrackingService: PerformanceTrackingService
    ): WebRequestInterceptor {
        return PerformanceTrackingInterceptor(performanceTrackingService)
    }

    @Bean
    fun getTracingDataOutputProcessorChain(
        @Autowired processors: List<TrackingDataOutputListener>
    ): TracingDataOutputListenerChain {
        return TracingDataOutputListenerChain(processors)
    }

    @Bean()
    fun getPerformanceMonitoringAdvice(
        @Autowired performanceTrackingService: PerformanceTrackingService
    ): PerformanceTrackingAdvice {
        return PerformanceTrackingAdvice(performanceTrackingService)
    }
}