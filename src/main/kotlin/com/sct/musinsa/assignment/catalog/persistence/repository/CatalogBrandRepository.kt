package com.sct.musinsa.assignment.catalog.persistence.repository

import com.sct.musinsa.assignment.catalog.persistence.domain.entity.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CatalogBrandRepository : JpaRepository<BrandEntity, Long> {
    fun findByBrandName(brandName: String): BrandEntity?
}