package com.sct.musinsa.assignment.catalog.persistence.domain


import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.BrandEntity
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.ProductEntity
import com.sct.musinsa.assignment.catalog.persistence.repository.CatalogBrandRepository
import com.sct.musinsa.assignment.catalog.persistence.repository.CatalogProductRepository
import com.sct.musinsa.assignment.catalog.persistence.repository.dsl.CategoryDslRepository
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandAllCategoryPriceVo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * aggregate 의 책임은 Domain Logic과 트랜잭션을 관리 한다.
 */
@Service
class CatalogAggregate(
    private val categoryDslRepository: CategoryDslRepository,
    private val catalogBrandRepository: CatalogBrandRepository,
    private val catalogProductRepository: CatalogProductRepository
) {
    @Transactional
    fun upsertCatalog(catalogs: List<Catalog>): List<Catalog> {
        return catalogs.map { catalog ->
            val savedBrand = catalogBrandRepository.findByBrandName(catalogs.first().brandName)
                ?: catalogBrandRepository.save(BrandEntity().apply {
                    brandName = catalogs.first().brandName
                })

            // 상품 저장
            val productCategory = ProductCategoryType.findByCode(catalog.productCategory)
            val product = catalogProductRepository.findByBrandAndProductCategory(savedBrand,productCategory)
                ?: ProductEntity()

            product.apply {
                this.productCategory = productCategory
                this.productPrice = catalog.productPrice
                this.brand = savedBrand
            }

            val savedProduct = catalogProductRepository.save(product)

            // 저장된 정보를 Catalog 객체로 반환
            Catalog(
                brandId = savedBrand.id,
                brandName = savedBrand.brandName ?: "",
                productId = savedProduct.id,
                productCategory = savedProduct.productCategory.code,
                productPrice = savedProduct.productPrice
            )
        }.toList()
    }

    @Transactional
    fun deleteCatalogByBrandId(brandId: Long) {
        val deleteCandidateBrand = catalogBrandRepository.findById(brandId).get()
        catalogBrandRepository.delete(deleteCandidateBrand)
    }

    @Transactional
    fun deleteProductByProductId(productId: Long) {
        catalogProductRepository.deleteById(productId)
    }

    fun findAllCategoryWithPriceOrderByCategoryAndPrice(): List<Catalog> {
        val productPriceList = categoryDslRepository.findAllCategoryWithPriceOrderByCategoryAndPrice()
        return productPriceList
    }

    fun findAllBrandWithSumAllCategoryPriceOrderByPrice(): List<BrandAllCategoryPriceVo> {
        val brandAllCategoryPriceList = categoryDslRepository.findAllBrandWithSumAllCategoryPriceOrderByPrice()
        return brandAllCategoryPriceList
    }
    fun findAllCategoryPriceByBrandId(brandId: Long): List<Catalog> {
        val brandAllCategoryPriceList = categoryDslRepository.findAllCategoryPriceByBrandId(brandId)
        return brandAllCategoryPriceList
    }
    fun findAllBrandPriceByCategoryOrderByPrice(productCategoryType: ProductCategoryType): List<Catalog>{
        val brandPriceList = categoryDslRepository.findAllBrandPriceByCategory(productCategoryType)
        return brandPriceList
    }

}