package com.sct.musinsa.assignment.catalog.service

import com.sct.musinsa.assignment.catalog.controller.payload.AdminCatalogCreateRequest
import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.persistence.domain.CatalogAggregate
import org.springframework.stereotype.Service

@Service
class AdminCatalogService(private val catalogAggregate: CatalogAggregate) {

    fun createCatalogs(requests: List<AdminCatalogCreateRequest>) {
        requests.forEach { request ->
            catalogAggregate.upsertCatalog(request.toCatalogs())
        }
    }

    fun deleteCatalogByBrandId(brandId: Long) {
        catalogAggregate.deleteCatalogByBrandId(brandId)
    }

    fun deleteProductByProductId(productId: Long) {
        catalogAggregate.deleteProductByProductId(productId)
    }
}
