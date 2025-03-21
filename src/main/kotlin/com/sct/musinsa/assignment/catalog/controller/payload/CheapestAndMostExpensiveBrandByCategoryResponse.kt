package com.sct.musinsa.assignment.catalog.controller.payload

import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandPriceVo

class CheapestAndMostExpensiveBrandByCategoryResponse(
    val category: String,
    val cheapest: BrandPriceVo,
    val expensive: BrandPriceVo
)