package com.sct.musinsa.assignment.catalog.service 
import com.sct.musinsa.assignment.catalog.controller.payload.BestPriceCatalogResponse
import com.sct.musinsa.assignment.catalog.persistence.domain.CatalogAggregate
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.repository.vo.BrandProductCategoryPriceVo
import com.sct.musinsa.assignment.common.http.exception.GlobalException
import com.sct.musinsa.assignment.common.http.response.Response
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CatalogServiceUnitTest : BehaviorSpec({
    val catalogAggregate = mockk<CatalogAggregate>()
    val catalogService = CatalogService(catalogAggregate)
//1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
    Given("[1.단위 테스트]카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회할 때"){
        When("브랜드 카테고리 별 상품 가격 조회에 성공 하면") {
            val result = mockFindAllCategoryWithPriceOrderByCategoryAndPrice()
            every { catalogAggregate.findAllCategoryWithPriceOrderByCategoryAndPrice() } returns result
        }
        Then("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 확인할 수 있다."){
            val realResponse = catalogService.findBestPriceWithAllCategory()
            val answer = correctAnswer1()
            var correctCount = 0
            answer.totalPrice shouldBe realResponse.totalPrice
            //응답 객체와 정답 객체의 값 비교를 통해 일치하는 것이 있는지 확인
            realResponse.bestPriceCategories.forEach { expectItem ->
                if(answer.bestPriceCategories.contains(expectItem)){
                    //일치하는 것이 있으면 카운트 증가
                    correctCount++
                }
            }
            //일치한 카운트가 기대 객체의 bestPriceCategories의 사이즈와 같으면 성공
            answer.bestPriceCategories.size shouldBe correctCount
        }
        When("브랜드 카테고리 별 상품 가격 조회에 실패 하면") {
            val result = mockEmptyFindAllCategoryWithPriceOrderByCategoryAndPrice()
            every { catalogAggregate.findAllCategoryWithPriceOrderByCategoryAndPrice() } returns result
        }
        Then("No Such Element GlobalException이 발생 한다") {
            val exception = shouldThrow<GlobalException> {
                catalogService.findBestPriceWithAllCategory()
            }
            exception.code shouldBe Response.ResponseCode.NO_SUCH_ELEMENT.code

        }

    }

})

// 문제1 번 Mock DATA
fun mockEmptyFindAllCategoryWithPriceOrderByCategoryAndPrice():List<BrandProductCategoryPriceVo>{
    return emptyList()
}

fun mockFindAllCategoryWithPriceOrderByCategoryAndPrice():List<BrandProductCategoryPriceVo> {
    val originalList = listOf(
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.TOP.code, price = 11200),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.OUTER.code, price = 5500),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.BOTTOM.code, price = 4200),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.SNEAKERS.code, price = 9000),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.BAG.code, price = 2000),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.CAP.code, price = 1700),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.SOCKS.code, price = 1800),
        BrandProductCategoryPriceVo(brandName="A", category = ProductCategoryType.ACCESSORY.code, price = 2300),

        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.TOP.code, price = 10500),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.OUTER.code, price = 5900),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.BOTTOM.code, price = 3800),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.SNEAKERS.code, price = 9100),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.BAG.code, price = 2100),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.CAP.code, price = 2000),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.SOCKS.code, price = 2000),
        BrandProductCategoryPriceVo(brandName="B", category = ProductCategoryType.ACCESSORY.code, price = 2200),

        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.TOP.code, price = 10000),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.OUTER.code, price = 6200),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.BOTTOM.code, price = 3300),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.SNEAKERS.code, price = 9200),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.BAG.code, price = 2200),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.CAP.code, price = 1900),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.SOCKS.code, price = 2200),
        BrandProductCategoryPriceVo(brandName="C", category = ProductCategoryType.ACCESSORY.code, price = 2100),

        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.TOP.code, price = 10100),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.OUTER.code, price = 5100),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.BOTTOM.code, price = 3000),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.SNEAKERS.code, price = 9500),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.BAG.code, price = 2500),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.CAP.code, price = 1500),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.SOCKS.code, price = 2400),
        BrandProductCategoryPriceVo(brandName="D", category = ProductCategoryType.ACCESSORY.code, price = 2000),

        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.TOP.code, price = 10700),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.OUTER.code, price = 5000),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.BOTTOM.code, price = 3800),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.SNEAKERS.code, price = 9900),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.BAG.code, price = 2300),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.CAP.code, price = 1800),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.SOCKS.code, price = 2100),
        BrandProductCategoryPriceVo(brandName="E", category = ProductCategoryType.ACCESSORY.code, price = 2100),

        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.TOP.code, price = 11200),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.OUTER.code, price = 7200),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.BOTTOM.code, price = 4000),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.SNEAKERS.code, price = 9300),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.BAG.code, price = 2100),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.CAP.code, price = 1600),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.SOCKS.code, price = 2300),
        BrandProductCategoryPriceVo(brandName="F", category = ProductCategoryType.ACCESSORY.code, price = 1900),

        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.TOP.code, price = 10500),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.OUTER.code, price = 5800),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.BOTTOM.code, price = 3900),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.SNEAKERS.code, price = 9000),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.BAG.code, price = 2200),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.CAP.code, price = 1700),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.SOCKS.code, price = 2100),
        BrandProductCategoryPriceVo(brandName="G", category = ProductCategoryType.ACCESSORY.code, price = 2000),

        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.TOP.code, price = 10800),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.OUTER.code, price = 6300),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.BOTTOM.code, price = 3100),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.SNEAKERS.code, price = 9700),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.BAG.code, price = 2100),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.CAP.code, price = 1600),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.SOCKS.code, price = 2000),
        BrandProductCategoryPriceVo(brandName="H", category = ProductCategoryType.ACCESSORY.code, price = 2000),

        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.TOP.code, price = 11400),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.OUTER.code, price = 6700),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.BOTTOM.code, price = 3200),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.SNEAKERS.code, price = 9500),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.BAG.code, price = 2400),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.CAP.code, price = 1700),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.SOCKS.code, price = 1700),
        BrandProductCategoryPriceVo(brandName="I", category = ProductCategoryType.ACCESSORY.code, price = 2400),
    )
    val sortedList = originalList.sortedWith(
        compareBy<BrandProductCategoryPriceVo> { it.category }
            .thenBy { it.price }
    )
    return sortedList
}