package com.sct.musinsa.assignment.catalog.service.dto

import com.querydsl.core.annotations.QueryProjection
import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import java.util.*

class BrandPriceDto
@QueryProjection constructor(
    var brandName: String = "",
    val price: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BrandPriceDto) return false

        return brandName.trim() == other.brandName.trim() &&
                price == other.price
    }

    override fun hashCode(): Int {
        return Objects.hash(this.brandName.trim(), price)
    }

    companion object {
        fun from(catalog: Catalog): BrandPriceDto {
            return BrandPriceDto(
                brandName = catalog.brandName,
                price = catalog.productPrice
            )
        }
    }
}