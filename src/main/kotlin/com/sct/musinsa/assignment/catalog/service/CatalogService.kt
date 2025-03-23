package com.sct.musinsa.assignment.catalog.service

import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceBrandForAllCategoriesResponse
import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceCatalogResponse
import com.sct.musinsa.assignment.catalog.controller.payload.CheapestAndMostExpensiveBrandByCategoryResponse
import com.sct.musinsa.assignment.catalog.persistence.domain.CatalogAggregate
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.service.dto.BrandPriceDto
import com.sct.musinsa.assignment.catalog.service.dto.BrandProductCategoryPriceDto
import com.sct.musinsa.assignment.catalog.service.dto.CategoryPriceDto
import com.sct.musinsa.assignment.common.http.exception.GlobalException
import com.sct.musinsa.assignment.common.http.response.Response
import org.springframework.stereotype.Service

//서비스에서는 비즈니스 요구사항을 처리한다.
@Service
class CatalogService(private val catalogAggregate: CatalogAggregate) {

    fun findBestPriceWithAllCategory(): BestPriceCatalogResponse {
        val productPriceList = catalogAggregate.findAllCategoryWithPriceOrderByCategoryAndPrice()
            .takeIf { it.isNotEmpty() }
            ?: throw GlobalException(Response.ResponseCode.NO_SUCH_ELEMENT)

        var currentCategory = productPriceList.last().productCategory

        val bestPriceList = productPriceList.filter { productPrice ->
            if (currentCategory != productPrice.productCategory) {
                currentCategory = productPrice.productCategory
                true
            } else {
                false
            }
        }.map { catalog->
            BrandProductCategoryPriceDto.from(catalog)
        }

        return BestPriceCatalogResponse(bestPriceList)
    }

    fun findBestPricedBrandForAllCategories(): BestPriceBrandForAllCategoriesResponse {
        val bestPriceBrand = catalogAggregate.findAllBrandWithSumAllCategoryPriceOrderByPrice().firstOrNull()
            ?: throw GlobalException(Response.ResponseCode.NO_SUCH_ELEMENT)

        val bestPriceBrandAndAllCategories = catalogAggregate.findAllCategoryPriceByBrandId(bestPriceBrand.brandId).map {
            CategoryPriceDto.from(it)
        }

        return BestPriceBrandForAllCategoriesResponse(bestPriceBrand.brandName, bestPriceBrandAndAllCategories)
    }

    fun findCheapestAndMostExpensiveBrandByCategory(productCategoryType: ProductCategoryType): CheapestAndMostExpensiveBrandByCategoryResponse{
        val allBrandPrices = catalogAggregate.findAllBrandPriceByCategoryOrderByPrice(productCategoryType)
            .takeIf { it.isNotEmpty() }
            ?.map {
                BrandPriceDto.from(it)
            }
            ?: throw GlobalException(Response.ResponseCode.NO_SUCH_ELEMENT)

        return CheapestAndMostExpensiveBrandByCategoryResponse(productCategoryType.koVal, allBrandPrices.first(), allBrandPrices.last())
    }
}
