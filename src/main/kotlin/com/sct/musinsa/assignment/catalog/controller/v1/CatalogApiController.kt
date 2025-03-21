package com.sct.musinsa.assignment.catalog.controller.v1

import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceBrandForAllCategoriesResponse
import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceCatalogResponse
import com.sct.musinsa.assignment.catalog.controller.payload.CheapestAndMostExpensiveBrandByCategoryResponse
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.service.CatalogService
import com.sct.musinsa.assignment.common.http.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/catalogs"])
class CatalogApiController(private val catalogService: CatalogService) {

    @GetMapping(path = ["/categories/best-price"])
    fun findBestPriceWithAllCategory(): ResponseEntity<Response<BestPriceCatalogResponse>>{
        return Response.okFrom(catalogService.findBestPriceWithAllCategory())
    }

    @GetMapping(path = ["/categories/{productCategory}"])
    fun findCheapestAndMostExpensiveBrandByCategory(
        @PathVariable("productCategory") productCategory: ProductCategoryType,
    ): ResponseEntity<Response<CheapestAndMostExpensiveBrandByCategoryResponse>>{
        return Response.okFrom(catalogService.findCheapestAndMostExpensiveBrandByCategory(productCategory))
    }

    @GetMapping(path = ["/brands/best-price"])
    fun findBestPricedBrandForAllCategories(): ResponseEntity<Response<BestPriceBrandForAllCategoriesResponse>>{

        return Response.okFrom(catalogService.findBestPricedBrandForAllCategories())
    }
}