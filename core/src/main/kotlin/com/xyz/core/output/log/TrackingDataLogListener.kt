package com.xyz.core.output.log

import com.xyz.core.model.PerformanceEvent
import com.xyz.core.model.PerformanceTrackingData
import com.xyz.core.output.TrackingDataOutputListener
import org.slf4j.LoggerFactory

class TrackingDataLogListener : TrackingDataOutputListener {

    private val log = LoggerFactory.getLogger(TrackingDataLogListener::class.java)

    override fun process(tracingData: PerformanceTrackingData) {
        log.info("\n" + toTrace(tracingData.trace))
    }

    private fun toTrace(event: PerformanceEvent, level: Int = 0): String {
        val sb = StringBuilder()

        val padLength = level * 2 + 4
        sb.append(event.durationMs.toString().padStart(padLength, ' '))
            .append("\t")
            .append(event.name)

        for (arg in event.args) {
            sb.append("\n  ")
                .append(" ".repeat(padLength))
                .append(arg.key).append(" = ").append(arg.value.toString())
        }
        sb.append("\n")

        for (child in event.children) {
            sb.append(toTrace(child, level + 1))
        }
        return sb.toString()
    }
}