package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank


class AdminCatalogCreateRequest(
    @field:NotBlank(message = "validation.bad-request.400")
    var brandName: String? = "",
    @field:Valid
    var productCategories: List<ProductCategory>
){
    fun toCatalogs(): List<Catalog> {
        return productCategories.map { category ->
            Catalog(
                brandId = null,
                brandName = this.brandName ?: "",
                productId = null,
                productCategory = category.productCategory ?: "",
                productPrice = category.productPrice
            )
        }
    }
}
class ProductCategory(
    @field:NotBlank(message = "validation.bad-request.400")
    var productCategory: String? = "",
    val productPrice: Long
)


