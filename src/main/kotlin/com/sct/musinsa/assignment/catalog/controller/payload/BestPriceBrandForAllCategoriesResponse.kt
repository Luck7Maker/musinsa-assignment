package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.service.dto.CategoryPriceDto


class BestPriceBrandForAllCategoriesResponse(
    val brandName: String,
    val bestPriceCategories: List<CategoryPriceDto>
) {
    val totalPrice: Long
        get() {
            return bestPriceCategories.sumOf { it.price }
        }
}