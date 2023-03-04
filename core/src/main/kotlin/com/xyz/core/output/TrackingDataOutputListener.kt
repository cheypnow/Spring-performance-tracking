package com.xyz.core.output

import com.xyz.core.model.PerformanceTrackingData

interface TrackingDataOutputListener {

    fun process(tracingData: PerformanceTrackingData)
}