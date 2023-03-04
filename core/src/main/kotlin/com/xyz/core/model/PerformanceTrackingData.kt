package com.xyz.core.model

data class PerformanceTrackingData(
    val endpoint: String,
    val duration: Long,
    val trace: PerformanceEvent
)