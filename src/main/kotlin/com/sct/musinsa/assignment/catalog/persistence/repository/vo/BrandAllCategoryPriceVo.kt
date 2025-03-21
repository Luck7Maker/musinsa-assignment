package com.sct.musinsa.assignment.catalog.persistence.repository.vo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.querydsl.core.annotations.QueryProjection
import java.util.*

class BrandAllCategoryPriceVo
@QueryProjection constructor(
    @JsonIgnore
    val brandId: Long,
    val brandName: String,
    val sumPrice: Long,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BrandAllCategoryPriceVo) return false

        return brandId == other.brandId &&
                brandName.trim() == other.brandName.trim() &&
                sumPrice == other.sumPrice
    }

    override fun hashCode(): Int {
        return Objects.hash(this.brandId,brandName.trim(),sumPrice)
    }
}