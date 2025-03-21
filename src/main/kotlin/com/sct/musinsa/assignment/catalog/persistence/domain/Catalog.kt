package com.sct.musinsa.assignment.catalog.persistence.domain

class Catalog (
    var brandId: Long?,
    val brandName: String,
    val productId: Long?,
    val productCategory: String,
    var productPrice: Long
)