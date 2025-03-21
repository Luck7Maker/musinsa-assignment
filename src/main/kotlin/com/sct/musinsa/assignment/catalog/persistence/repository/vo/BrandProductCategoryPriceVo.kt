package com.sct.musinsa.assignment.catalog.persistence.repository.vo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.querydsl.core.annotations.QueryProjection
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import java.util.*

class BrandProductCategoryPriceVo
@QueryProjection constructor(
    @JsonIgnore
    var category: String = "",
    val brandName: String,
    val price: Long,
) {

    val categoryKo: String
        get() {
            return ProductCategoryType.findByCode(this.category).koVal
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BrandProductCategoryPriceVo) return false

        return brandName.trim() == other.brandName.trim() &&
                category.trim() == other.category.trim() &&
                price == other.price
    }

    override fun hashCode(): Int {
        return Objects.hash(brandName, category, price)
    }
}