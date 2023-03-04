package com.xyz.core

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.WebRequestInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class InterceptorConfig : WebMvcConfigurationSupport() {

    private val log = LoggerFactory.getLogger(InterceptorConfig::class.java)

    @Autowired
    private lateinit var perfInterceptor: WebRequestInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        log.info("Register interceptor: $perfInterceptor")
        registry.addWebRequestInterceptor(perfInterceptor)
    }
}