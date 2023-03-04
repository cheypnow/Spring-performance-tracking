package com.xyz.core.monitoring

import com.xyz.core.model.PerformanceEvent
import com.xyz.core.model.PerformanceTrackingData
import com.xyz.core.output.TracingDataOutputListenerChain
import org.apache.commons.lang3.StringUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.aspectj.lang.reflect.CodeSignature
import org.slf4j.LoggerFactory

class DefaultPerformanceTrackingService(
    private val dataOutputProcessorChain: TracingDataOutputListenerChain
) : PerformanceTrackingService {

    private val log = LoggerFactory.getLogger(DefaultPerformanceTrackingService::class.java)

    private var init = false

    private var requestStartTs: Long = -1
    private lateinit var endpointName: String

    private lateinit var rootEvent: PerformanceEvent
    private lateinit var currentRootEvent: PerformanceEvent

    override fun registerNewRequest(endpointName: String) {
        log.debug("Register new request $endpointName")
        init = true
        requestStartTs = System.currentTimeMillis()
        this.endpointName = endpointName

        rootEvent = PerformanceEvent(endpointName, requestStartTs)
        currentRootEvent = rootEvent
    }

    override fun proceedJoinPoint(jp: ProceedingJoinPoint): Any? {
        log.debug("Proceed join point: $jp")
        if (!init) {
            log.debug("Skip join point: $jp. Monitoring service has not been initialized.")
            return jp.proceed()
        }

        val startTs = System.currentTimeMillis()

        // class field may be change later, so parent event must be stored on function level
        val localParent = currentRootEvent

        val event = createEvent(jp, startTs)

        localParent.children.add(event)

        currentRootEvent = event

        log.debug("parent: ${localParent.name}")

        val jpResult = jp.proceed()

        val duration: Long = System.currentTimeMillis() - startTs

        event.durationMs = duration

        log.debug("event: ${event.name}")

        currentRootEvent = localParent

        return jpResult
    }

    private fun createEvent(
        jp: ProceedingJoinPoint,
        startTs: Long
    ): PerformanceEvent {
        val event = PerformanceEvent(name(jp), startTs)

        for ((i, name) in (jp.signature as CodeSignature).parameterNames.withIndex()) {
            event.args[name] = jp.args[i]
        }
        return event
    }

    private fun name(jp: JoinPoint): String {
        val signature: Signature = jp.signature
        val classShortName: String = StringUtils.substringAfterLast(signature.declaringTypeName, ".")
        return "$classShortName.${signature.name}"
    }

    override fun finishRequest() {
        log.debug("Finish request $endpointName")
        if (!init) return

        val duration = System.currentTimeMillis() - requestStartTs

        rootEvent.durationMs = duration

        val tracingData = PerformanceTrackingData(endpointName, duration, rootEvent)

        dataOutputProcessorChain.process(tracingData)
    }
}