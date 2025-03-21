package com.sct.musinsa.assignment.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslJpaConfig {
    @Bean
    fun jpaQueryFactory(em: EntityManager): JPAQueryFactory {
        return JPAQueryFactory(em)
    }
}