package com.sct.musinsa.assignment.catalog.controller.v1

import com.sct.musinsa.assignment.catalog.controller.payload.AdminCatalogCreateRequest
import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.service.AdminCatalogService
import com.sct.musinsa.assignment.common.http.response.Response
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/admin"])
class AdminCatalogApiController(private val adminCatalogService: AdminCatalogService) {

    @PostMapping(path = ["/catalogs"])
    fun createCatalogs(
        @RequestBody @Valid requests: List<AdminCatalogCreateRequest>,
    ): ResponseEntity<Response<String>> {
        adminCatalogService.createCatalogs(requests)
        return Response.ok()
    }

    @DeleteMapping(path = ["/catalogs/{brandId}"])
    fun deleteCatalog(
        @PathVariable("brandId") brandId: Long,
    ): ResponseEntity<Response<String>>{
        adminCatalogService.deleteCatalogByBrandId(brandId)
        return Response.ok()
    }
    @DeleteMapping(path = ["/catalogs/products/{productId}"])
    fun deleteProduct(
        @PathVariable("productId") productId: Long,
    ): ResponseEntity<Response<String>>{
        adminCatalogService.deleteProductByProductId(productId)
        return Response.ok()
    }
}