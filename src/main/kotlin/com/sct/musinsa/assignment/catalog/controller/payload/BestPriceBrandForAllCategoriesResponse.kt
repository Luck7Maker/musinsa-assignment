package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.persistence.repository.vo.CategoryPriceVo


class BestPriceBrandForAllCategoriesResponse(
    val brandName: String,
    val bestPriceCategories: List<CategoryPriceVo>
) {
    val totalPrice: Long
        get() {
            return bestPriceCategories.sumOf { it.price }
        }
}