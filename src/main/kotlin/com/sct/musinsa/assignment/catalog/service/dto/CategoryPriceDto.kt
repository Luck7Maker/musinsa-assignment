package com.sct.musinsa.assignment.catalog.service.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.querydsl.core.annotations.QueryProjection
import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import java.util.*

class CategoryPriceDto
@QueryProjection constructor(
    @JsonIgnore
    var category: String = "",
    val price: Long,
) {

    val categoryKo: String
        get() {
            return ProductCategoryType.findByCode(this.category).koVal
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CategoryPriceDto) return false

        return  category.trim() == other.category.trim() &&
                price == other.price
    }

    override fun hashCode(): Int {
        return Objects.hash(category.trim(), price)
    }
    companion object {
        fun from(catalog: Catalog): CategoryPriceDto {
            return CategoryPriceDto(
                category = catalog.productCategory,
                price = catalog.productPrice
            )
        }
    }
}