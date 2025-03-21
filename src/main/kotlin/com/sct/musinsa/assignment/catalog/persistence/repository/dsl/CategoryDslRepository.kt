package com.sct.musinsa.assignment.catalog.persistence.repository.dsl

import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandAllCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandProductCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.CategoryPriceVo

interface CategoryDslRepository {
    fun findAllCategoryWithPriceOrderByCategoryAndPrice(): List<BrandProductCategoryPriceVo>
    fun findAllBrandWithSumAllCategoryPriceOrderByPrice(): List<BrandAllCategoryPriceVo>
    fun findAllCategoryPriceByBrandId(brandId: Long): List<CategoryPriceVo>
    fun findAllBrandPriceByCategory(productCategoryType: ProductCategoryType): List<BrandPriceVo>
}