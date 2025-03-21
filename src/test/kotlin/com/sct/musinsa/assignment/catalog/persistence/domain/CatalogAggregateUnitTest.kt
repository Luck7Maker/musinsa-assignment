package com.sct.musinsa.assignment.catalog.persistence.domain

import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.repository.CatalogBrandRepository
import com.sct.musinsa.assignment.catalog.persistence.repository.CatalogProductRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.beans.factory.annotation.Autowired
import kotlin.jvm.optionals.getOrNull

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CatalogAggregateBehaviorSpecTest :  BehaviorSpec() {

    @Autowired
    private lateinit var catalogAggregate: CatalogAggregate
    @Autowired
    private lateinit var catalogBrandRepository: CatalogBrandRepository
    @Autowired
    private lateinit var catalogProductRepository: CatalogProductRepository


    init {
        Given("[4.유닛 테스트] 신규 A 브랜드/상품 등록,수정,삭제") {
            // 테스트 전 실행 (Spring bean 주입 이후 실행됨)
            val originCatalogs = createCatalogSampleData()
            val createdCatalogs = catalogAggregate.upsertCatalog(originCatalogs)

            When("신규 브랜드 A 와 상품을(상의, 하의, 아우터) 생성 하면") {
                Then("새로운 브랜드 A 와 상품들(상의, 하의, 아우터)이 생성 된다") {
                    createdCatalogs.forEachIndexed { index, created ->
                        val origineCatalog = originCatalogs[index]
                        created.brandName shouldBe origineCatalog.brandName
                        created.productCategory shouldBe origineCatalog.productCategory
                        created.productPrice shouldBe origineCatalog.productPrice
                    }
                }

            }
            When("브랜드 A 의 악세사리(accessory ,4000원)를 추가 하면"){
                val newCatalogs = listOf(
                    Catalog(
                        brandId = null,
                        brandName = "A",
                        productId = null,
                        productCategory = ProductCategoryType.ACCESSORY.code,
                        productPrice = 4000
                    )
                )
                val createdCatalogs = catalogAggregate.upsertCatalog(newCatalogs)

                Then("브랜드 A에 악세사리(Accessory) 상품이 추가 되어야 한다") {
                    createdCatalogs.forEachIndexed { index, created ->
                        val origineCatalog = newCatalogs[index]
                        created.brandId shouldBe 1
                        created.brandName shouldBe origineCatalog.brandName
                        created.productCategory shouldBe origineCatalog.productCategory
                        created.productPrice shouldBe origineCatalog.productPrice
                    }
                }
            }
            When("브랜드 A 의 아우터(outer) 가격을 100원 할증 하면") {
                val originPrice = originCatalogs.find { it.productCategory == ProductCategoryType.OUTER.code }?.productPrice
                val modifiedCatalogs = originCatalogs.filter { it.productCategory == ProductCategoryType.OUTER.code }
                    .map {
                        it.productPrice += 100
                        it
                    }
                val updatedCatalogs = catalogAggregate.upsertCatalog(modifiedCatalogs)
                Then("아우터(outer) 상품 가격이 100원 할증 되어야 한다") {
                    updatedCatalogs.forEachIndexed { index, updated ->
                        updated.productPrice shouldBe originPrice!! + 100
                        updated.productPrice shouldBe modifiedCatalogs[index].productPrice
                    }
                }
            }
            When("브랜드 A 의 상의(top) 상품을 삭제 하면"){
                val deletedCatalogs = createdCatalogs.filter { it.productCategory == ProductCategoryType.TOP.code }

                val deleteProductId = deletedCatalogs.first().productId
                catalogAggregate.deleteProductByProductId(deleteProductId!!)
                Then("브랜드 A 와 상의(top) 상품이 삭제 되어야 한다") {
                    val deletedProduct = catalogProductRepository.findById(deleteProductId)
                    deletedProduct.isEmpty shouldBe true
                }
            }
            When("브랜드 A 를 삭제 하면"){
                val orginBrand = catalogBrandRepository.findById(createdCatalogs[0].brandId!!).getOrNull()
                catalogAggregate.deleteCatalogByBrandId(createdCatalogs[0].brandId!!)

                Then("브랜드 A 와 모든 상품들이 삭제 되어야 한다") {
                    val deletedBrand = catalogBrandRepository.findById(createdCatalogs[0].brandId!!)
                    val deletedProducts = catalogProductRepository.findByBrand(orginBrand!!)
                    deletedBrand.isEmpty shouldBe true
                    deletedProducts!!.isEmpty() shouldBe true
                }
            }
        }
   }
}

fun createCatalogSampleData(): List<Catalog> {
    val newCatalogs = listOf(
        Catalog(
            brandId = null,
            brandName = "A",
            productId = null,
            productCategory = ProductCategoryType.TOP.code,
            productPrice = 11200
        ),
        Catalog(
            brandId = null,
            brandName = "A",
            productId = null,
            productCategory = ProductCategoryType.OUTER.code,
            productPrice = 5500
        ),
        Catalog(
            brandId = null,
            brandName = "A",
            productId = null,
            productCategory = ProductCategoryType.BOTTOM.code,
            productPrice = 4200
        )
    )
    return newCatalogs
}
