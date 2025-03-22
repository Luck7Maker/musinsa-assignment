package com.sct.musinsa.assignment.common.config

import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryTypeMvcConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(ProductCategoryTypeMvcConverter())
    }
}