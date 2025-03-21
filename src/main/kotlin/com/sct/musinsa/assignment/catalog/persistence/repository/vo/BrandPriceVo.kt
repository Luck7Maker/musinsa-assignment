package com.sct.musinsa.assignment.catalog.persistence.repository.vo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.querydsl.core.annotations.QueryProjection
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import java.util.*

class BrandPriceVo
@QueryProjection constructor(
    var brandName: String = "",
    val price: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BrandPriceVo) return false

        return brandName.trim() == other.brandName.trim() &&
                price == other.price
    }

    override fun hashCode(): Int {
        return Objects.hash(this.brandName.trim(), price)
    }
}