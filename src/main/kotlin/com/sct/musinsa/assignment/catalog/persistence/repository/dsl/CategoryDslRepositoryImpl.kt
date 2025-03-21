package com.sct.musinsa.assignment.catalog.persistence.repository.dsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.QProductEntity.productEntity
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.QBrandEntity.brandEntity
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandAllCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandProductCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.CategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.QBrandAllCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.QBrandPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.QBrandProductCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.QCategoryPriceVo
import org.springframework.stereotype.Repository

@Repository
class CategoryDslRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory): CategoryDslRepository {
    override fun findAllCategoryWithPriceOrderByCategoryAndPrice(): List<BrandProductCategoryPriceVo> {
        return jpaQueryFactory
            .select(
                QBrandProductCategoryPriceVo(
                    productEntity.productCategory.stringValue(),
                    brandEntity.brandName,
                    productEntity.productPrice
                )
            )
            .from(productEntity)
            .join(productEntity.brand, brandEntity)
            .orderBy(productEntity.productCategory.asc(),productEntity.productPrice.asc())
            .fetch()
    }

    override fun findAllBrandWithSumAllCategoryPriceOrderByPrice(): List<BrandAllCategoryPriceVo>{
        return  jpaQueryFactory
            .select(
                QBrandAllCategoryPriceVo(
                    productEntity.brand.id,
                    productEntity.brand.brandName,
                    productEntity.productPrice.sum()
                )
            )
            .from(productEntity)
            .groupBy(productEntity.brand.id, productEntity.brand.brandName,)
            .orderBy(productEntity.productPrice.sum().asc())
            .fetch()
    }
    override fun findAllCategoryPriceByBrandId(brandId: Long): List<CategoryPriceVo>{
        return jpaQueryFactory
            .select(
                QCategoryPriceVo(
                    productEntity.productCategory.stringValue(),
                    productEntity.productPrice
                )
            )
            .from(productEntity)
            .join(productEntity.brand, brandEntity)
            .where(
                brandEntity.id.eq(brandId)
            )
            .orderBy(productEntity.productCategory.asc())
            .fetch()
    }

    override fun findAllBrandPriceByCategory(productCategoryType: ProductCategoryType): List<BrandPriceVo> {
        return jpaQueryFactory
            .select(
                QBrandPriceVo(
                    brandEntity.brandName,
                    productEntity.productPrice
                )
            )
            .from(productEntity)
            .join(productEntity.brand, brandEntity)
            .where(
                productEntity.productCategory.eq(productCategoryType)
            )
            .orderBy(productEntity.productPrice.asc())
            .fetch()
    }
}