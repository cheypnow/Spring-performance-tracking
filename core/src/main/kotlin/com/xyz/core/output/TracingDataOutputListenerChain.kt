package com.xyz.core.output

import com.xyz.core.model.PerformanceTrackingData
import org.slf4j.LoggerFactory

class TracingDataOutputListenerChain(private val listeners: List<TrackingDataOutputListener>) {

    private val log = LoggerFactory.getLogger(TracingDataOutputListenerChain::class.java)

    init {
        for (listener in listeners) {
            log.info("Registered tracing data listener: $listener")
        }
    }

    fun process(tracingData: PerformanceTrackingData) {
        for (listener in listeners) {
            listener.process(tracingData)
        }
    }
}