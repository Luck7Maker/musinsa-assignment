package com.sct.musinsa.assignment.catalog.persistence.repository.dsl

import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandAllCategoryPriceVo

interface CategoryDslRepository {
    fun findAllCategoryWithPriceOrderByCategoryAndPrice(): List<Catalog>
    fun findAllBrandWithSumAllCategoryPriceOrderByPrice(): List<BrandAllCategoryPriceVo>
    fun findAllCategoryPriceByBrandId(brandId: Long): List<Catalog>
    fun findAllBrandPriceByCategory(productCategoryType: ProductCategoryType): List<Catalog>
}