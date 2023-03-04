package com.xyz.core.model

data class PerformanceEvent(
    val name: String,
    val startMs: Long,
    var durationMs: Long = -1,
    val args: MutableMap<String, Any> = HashMap(),
    val children: MutableList<PerformanceEvent> = mutableListOf()
)