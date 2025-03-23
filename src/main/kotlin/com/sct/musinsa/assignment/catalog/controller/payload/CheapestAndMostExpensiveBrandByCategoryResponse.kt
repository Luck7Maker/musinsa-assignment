package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.service.dto.BrandPriceDto

class CheapestAndMostExpensiveBrandByCategoryResponse(
    val category: String,
    val cheapest: BrandPriceDto,
    val expensive: BrandPriceDto
)