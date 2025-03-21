package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandProductCategoryPriceVo


class BestPriceCatalogResponse(
    val bestPriceCategories: List<BrandProductCategoryPriceVo>
) {
    val totalPrice: Long
        get() {
            return bestPriceCategories.sumOf { it.price }
        }
}