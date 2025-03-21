package com.sct.musinsa.assignment.catalog.service 
import com.sct.musinsa.assignment.catalog.controller.payload.AdminCatalogCreateRequest
import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceBrandForAllCategoriesResponse
import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceCatalogResponse
import com.sct.musinsa.assignment.catalog.controller.payload.CheapestAndMostExpensiveBrandByCategoryResponse
import com.sct.musinsa.assignment.catalog.persistence.domain.CatalogAggregate
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandProductCategoryPriceVo
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.CategoryPriceVo
import com.sct.musinsa.assignment.common.http.exception.GlobalException
import com.sct.musinsa.assignment.common.http.response.Response
import com.sct.musinsa.assignment.common.util.JsonReader
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CatalogServiceIntgTest : BehaviorSpec() {
    @Autowired
    private lateinit var catalogService: CatalogService

    init {

        Given("[1.통합 테스트]카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회할 때") {
            When("브랜드 카테고리 별 상품 가격 조회에 성공 하면") {
                val realResponse = catalogService.findBestPriceWithAllCategory()
                val answer = correctAnswer1()
                var expectedCount = 0

                Then("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 확인할 수 있다.") {
                    answer.totalPrice shouldBe realResponse.totalPrice
                    //응답 객체와 정답 객체의 값 비교를 통해 일치하는 것이 있는지 확인
                    realResponse.bestPriceCategories.forEach { expectItem ->
                        if (answer.bestPriceCategories.contains(expectItem)) {
                            //일치하는 것이 있으면 카운트 증가
                            expectedCount++
                        }
                    }
                    //일치한 카운트가 기대 객체의 bestPriceCategories의 사이즈와 같으면 성공
                    answer.bestPriceCategories.size shouldBe expectedCount
                }
            }
        }
        Given("[2통합 테스트]단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회") {
            When("최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회 하면"){
                val realResponse = catalogService.findBestPricedBrandForAllCategories()
                val answer = correctAnswer2()
                var expectedCount = 0

                Then("최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 확인할 수 있다."){
                    realResponse.brandName shouldBe answer.brandName
                    realResponse.totalPrice shouldBe answer.totalPrice
                    realResponse.bestPriceCategories.forEach { expectItem ->
                        if(answer.bestPriceCategories.contains(expectItem)){
                            //일치하는 것이 있으면 카운트 증가
                            expectedCount++
                        }
                    }
                    answer.bestPriceCategories.size shouldBe expectedCount
                }
            }

        }
        Given("[3통합 테스트]카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회") {
            When("카테고리 이름 으로 최저, 최고 가격 브랜드와 상품 가격을 조회 하면"){
                val realResponse = catalogService.findCheapestAndMostExpensiveBrandByCategory(ProductCategoryType.TOP)
                val answer = correctAnswer3()
                Then("해당 카테고리의 최저, 최고 가격의 브랜드와 상품 가격을 확인할 수 있다."){
                    realResponse.category shouldBe answer.category
                    realResponse.cheapest shouldBe answer.cheapest
                    realResponse.expensive shouldBe answer.expensive
                }
            }

        }
    }
}


//1번. 정답 객체 생성
fun correctAnswer1(): BestPriceCatalogResponse {
    val bestPriceList = listOf(
        BrandProductCategoryPriceVo(category = ProductCategoryType.ACCESSORY.code , brandName = "F", price = 1900),
        BrandProductCategoryPriceVo(category = ProductCategoryType.BAG.code , brandName = "A", price = 2000),
        BrandProductCategoryPriceVo(category = ProductCategoryType.BOTTOM.code , brandName = "D", price = 3000),
        BrandProductCategoryPriceVo(category = ProductCategoryType.CAP.code , brandName = "D", price = 1500),
        BrandProductCategoryPriceVo(category = ProductCategoryType.OUTER.code , brandName = "E", price = 5000),
        BrandProductCategoryPriceVo(category = ProductCategoryType.SNEAKERS.code , brandName = "A", price = 9000),
        BrandProductCategoryPriceVo(category = ProductCategoryType.SOCKS.code , brandName = "I", price = 1700),
        BrandProductCategoryPriceVo(category = ProductCategoryType.TOP.code , brandName = "C", price = 10000)
    )

    return BestPriceCatalogResponse(bestPriceCategories = bestPriceList)
}

//2번. 정답 객체 생성
fun correctAnswer2(): BestPriceBrandForAllCategoriesResponse{
    return BestPriceBrandForAllCategoriesResponse(
        brandName = "D",
        bestPriceCategories = listOf(
            CategoryPriceVo(
                category = ProductCategoryType.ACCESSORY.code,
                price = 2000
            ),
            CategoryPriceVo(
                category = ProductCategoryType.BAG.code,
                price = 2500
            ),
            CategoryPriceVo(
                category = ProductCategoryType.BOTTOM.code,
                price = 3000
            ),
            CategoryPriceVo(
                category = ProductCategoryType.CAP.code,
                price = 1500
            ),
            CategoryPriceVo(
                category = ProductCategoryType.OUTER.code,
                price = 5100
            ),
            CategoryPriceVo(
                category = ProductCategoryType.SNEAKERS.code,
                price = 9500
            ),
            CategoryPriceVo(
                category = ProductCategoryType.SOCKS.code,
                price = 2400
            ),
            CategoryPriceVo(
                category = ProductCategoryType.TOP.code,
                price = 10100
            )
        )
    )

}

//2번. 정답 객체 생성
fun correctAnswer3(): CheapestAndMostExpensiveBrandByCategoryResponse {
    return CheapestAndMostExpensiveBrandByCategoryResponse(
        category = "상의",
        cheapest = BrandPriceVo(brandName = "C", price = 10000),
        expensive = BrandPriceVo(brandName = "I", price = 11400)
    )
}