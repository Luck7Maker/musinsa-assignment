package com.sct.musinsa.assignment.catalog.persistence.repository

import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.BrandEntity
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CatalogProductRepository: JpaRepository<ProductEntity, Long>  {
    fun findByBrandAndProductCategory(brand: BrandEntity, productCategory: ProductCategoryType): ProductEntity?
    fun findByBrand(brand: BrandEntity): List<ProductEntity>?

}