package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.service.dto.BrandProductCategoryPriceDto


class BestPriceCatalogResponse(
    val bestPriceCategories: List<BrandProductCategoryPriceDto>
) {
    val totalPrice: Long
        get() {
            return bestPriceCategories.sumOf { it.price }
        }
}