package com.sct.musinsa.assignment.catalog.persistence.domain

import com.querydsl.core.annotations.QueryProjection
import java.util.*

class Catalog @QueryProjection constructor(
    var brandId: Long? = 0,
    val brandName: String = "",
    val productId: Long? = 0,
    val productCategory: String = "",
    var productPrice: Long = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Catalog) return false

        return brandId == other.brandId &&
                brandName.trim() == other.brandName.trim() &&
                productId == other.productId &&
                productCategory.trim() == other.productCategory.trim() &&
                productPrice == other.productPrice
    }

    override fun hashCode(): Int {
        return Objects.hash(brandId, brandName.trim(), productId, productCategory.trim(), productPrice)
    }
}
