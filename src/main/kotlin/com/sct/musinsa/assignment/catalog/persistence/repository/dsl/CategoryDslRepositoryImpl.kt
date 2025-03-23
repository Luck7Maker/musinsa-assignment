package com.sct.musinsa.assignment.catalog.persistence.repository.dsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.QProductEntity.productEntity
import com.sct.musinsa.assignment.catalog.persistence.domain.entity.QBrandEntity.brandEntity
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandAllCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.QBrandAllCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.domain.QCatalog
import org.springframework.stereotype.Repository

@Repository
class CategoryDslRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory): CategoryDslRepository {
    override fun findAllCategoryWithPriceOrderByCategoryAndPrice(): List<Catalog> {
        return jpaQueryFactory
            .select(
                QCatalog(
                    productEntity.brand.id,
                    brandEntity.brandName,
                    productEntity.id,
                    productEntity.productCategory.stringValue(),
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
    override fun findAllCategoryPriceByBrandId(brandId: Long): List<Catalog>{
        return jpaQueryFactory
            .select(
                QCatalog(
                    productEntity.brand.id,
                    brandEntity.brandName,
                    productEntity.id,
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

    override fun findAllBrandPriceByCategory(productCategoryType: ProductCategoryType): List<Catalog> {
        return jpaQueryFactory
            .select(
                QCatalog(
                    productEntity.brand.id,
                    brandEntity.brandName,
                    productEntity.id,
                    productEntity.productCategory.stringValue(),
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