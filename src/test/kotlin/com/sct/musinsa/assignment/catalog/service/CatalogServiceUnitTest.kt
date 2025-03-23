package com.sct.musinsa.assignment.catalog.service 
import com.sct.musinsa.assignment.catalog.persistence.domain.Catalog
import com.sct.musinsa.assignment.catalog.persistence.domain.CatalogAggregate
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.service.dto.BrandProductCategoryPriceDto
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
fun mockEmptyFindAllCategoryWithPriceOrderByCategoryAndPrice():List<Catalog>{
    return emptyList()
}

fun mockFindAllCategoryWithPriceOrderByCategoryAndPrice():List<Catalog> {
    val originalList = listOf(
        Catalog(brandId =1 ,brandName="A", productId = 1,productCategory = ProductCategoryType.TOP.code, productPrice = 11200),
        Catalog(brandId =1 ,brandName="A", productId = 2,productCategory = ProductCategoryType.OUTER.code, productPrice = 5500),
        Catalog(brandId =1 ,brandName="A", productId = 3,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 4200),
        Catalog(brandId =1 ,brandName="A", productId = 4,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9000),
        Catalog(brandId =1 ,brandName="A", productId = 5,productCategory = ProductCategoryType.BAG.code, productPrice = 2000),
        Catalog(brandId =1 ,brandName="A", productId = 6,productCategory = ProductCategoryType.CAP.code, productPrice = 1700),
        Catalog(brandId =1 ,brandName="A", productId = 7,productCategory = ProductCategoryType.SOCKS.code, productPrice = 1800),
        Catalog(brandId =1 ,brandName="A", productId = 8,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2300),

        Catalog(brandId =2 ,brandName="B", productId = 9,productCategory = ProductCategoryType.TOP.code, productPrice = 10500),
        Catalog(brandId =2 ,brandName="B", productId = 10,productCategory = ProductCategoryType.OUTER.code, productPrice = 5900),
        Catalog(brandId =2 ,brandName="B", productId = 11,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3800),
        Catalog(brandId =2 ,brandName="B", productId = 12,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9100),
        Catalog(brandId =2 ,brandName="B", productId = 13,productCategory = ProductCategoryType.BAG.code, productPrice = 2100),
        Catalog(brandId =2 ,brandName="B", productId = 14,productCategory = ProductCategoryType.CAP.code, productPrice = 2000),
        Catalog(brandId =2 ,brandName="B", productId = 15,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2000),
        Catalog(brandId =2 ,brandName="B", productId = 16,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2200),

        Catalog(brandId =3 ,brandName="C", productId = 17,productCategory = ProductCategoryType.TOP.code, productPrice = 10000),
        Catalog(brandId =3 ,brandName="C", productId = 18,productCategory = ProductCategoryType.OUTER.code, productPrice = 6200),
        Catalog(brandId =3 ,brandName="C", productId = 19,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3300),
        Catalog(brandId =3 ,brandName="C", productId = 20,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9200),
        Catalog(brandId =3 ,brandName="C", productId = 21,productCategory = ProductCategoryType.BAG.code, productPrice = 2200),
        Catalog(brandId =3 ,brandName="C", productId = 22,productCategory = ProductCategoryType.CAP.code, productPrice = 1900),
        Catalog(brandId =3 ,brandName="C", productId = 23,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2200),
        Catalog(brandId =3 ,brandName="C", productId = 24,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2100),

        Catalog(brandId =4 ,brandName="D", productId = 25,productCategory = ProductCategoryType.TOP.code, productPrice = 10100),
        Catalog(brandId =4 ,brandName="D", productId = 26,productCategory = ProductCategoryType.OUTER.code, productPrice = 5100),
        Catalog(brandId =4 ,brandName="D", productId = 27,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3000),
        Catalog(brandId =4 ,brandName="D", productId = 28,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9500),
        Catalog(brandId =4 ,brandName="D", productId = 29,productCategory = ProductCategoryType.BAG.code, productPrice = 2500),
        Catalog(brandId =4 ,brandName="D", productId = 30,productCategory = ProductCategoryType.CAP.code, productPrice = 1500),
        Catalog(brandId =4 ,brandName="D", productId = 31,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2400),
        Catalog(brandId =4 ,brandName="D", productId = 32,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2000),

        Catalog(brandId =5 ,brandName="E", productId = 33,productCategory = ProductCategoryType.TOP.code, productPrice = 10700),
        Catalog(brandId =5 ,brandName="E", productId = 34,productCategory = ProductCategoryType.OUTER.code, productPrice = 5000),
        Catalog(brandId =5 ,brandName="E", productId = 35,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3800),
        Catalog(brandId =5 ,brandName="E", productId = 36,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9900),
        Catalog(brandId =5 ,brandName="E", productId = 37,productCategory = ProductCategoryType.BAG.code, productPrice = 2300),
        Catalog(brandId =5 ,brandName="E", productId = 38,productCategory = ProductCategoryType.CAP.code, productPrice = 1800),
        Catalog(brandId =5 ,brandName="E", productId = 39,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2100),
        Catalog(brandId =5 ,brandName="E", productId = 40,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2100),

        Catalog(brandId =6 ,brandName="F", productId = 41,productCategory = ProductCategoryType.TOP.code, productPrice = 11200),
        Catalog(brandId =6 ,brandName="F", productId = 42,productCategory = ProductCategoryType.OUTER.code, productPrice = 7200),
        Catalog(brandId =6 ,brandName="F", productId = 43,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 4000),
        Catalog(brandId =6 ,brandName="F", productId = 44,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9300),
        Catalog(brandId =6 ,brandName="F", productId = 45,productCategory = ProductCategoryType.BAG.code, productPrice = 2100),
        Catalog(brandId =6 ,brandName="F", productId = 46,productCategory = ProductCategoryType.CAP.code, productPrice = 1600),
        Catalog(brandId =6 ,brandName="F", productId = 47,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2300),
        Catalog(brandId =6 ,brandName="F", productId = 48,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 1900),

        Catalog(brandId =7 ,brandName="G", productId = 49,productCategory = ProductCategoryType.TOP.code, productPrice = 10500),
        Catalog(brandId =7 ,brandName="G", productId = 50,productCategory = ProductCategoryType.OUTER.code, productPrice = 5800),
        Catalog(brandId =7 ,brandName="G", productId = 51,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3900),
        Catalog(brandId =7 ,brandName="G", productId = 52,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9000),
        Catalog(brandId =7 ,brandName="G", productId = 53,productCategory = ProductCategoryType.BAG.code, productPrice = 2200),
        Catalog(brandId =7 ,brandName="G", productId = 54,productCategory = ProductCategoryType.CAP.code, productPrice = 1700),
        Catalog(brandId =7 ,brandName="G", productId = 55,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2100),
        Catalog(brandId =7 ,brandName="G", productId = 56,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2000),

        Catalog(brandId =8 ,brandName="H", productId = 57,productCategory = ProductCategoryType.TOP.code, productPrice = 10800),
        Catalog(brandId =8 ,brandName="H", productId = 58,productCategory = ProductCategoryType.OUTER.code, productPrice = 6300),
        Catalog(brandId =8 ,brandName="H", productId = 59,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3100),
        Catalog(brandId =8 ,brandName="H", productId = 60,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9700),
        Catalog(brandId =8 ,brandName="H", productId = 61,productCategory = ProductCategoryType.BAG.code, productPrice = 2100),
        Catalog(brandId =8 ,brandName="H", productId = 62,productCategory = ProductCategoryType.CAP.code, productPrice = 1600),
        Catalog(brandId =8 ,brandName="H", productId = 63,productCategory = ProductCategoryType.SOCKS.code, productPrice = 2000),
        Catalog(brandId =8 ,brandName="H", productId = 64,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2000),

        Catalog(brandId =9 ,brandName="I", productId = 65,productCategory = ProductCategoryType.TOP.code, productPrice = 11400),
        Catalog(brandId =9 ,brandName="I", productId = 66,productCategory = ProductCategoryType.OUTER.code, productPrice = 6700),
        Catalog(brandId =9 ,brandName="I", productId = 67,productCategory = ProductCategoryType.BOTTOM.code, productPrice = 3200),
        Catalog(brandId =9 ,brandName="I", productId = 68,productCategory = ProductCategoryType.SNEAKERS.code, productPrice = 9500),
        Catalog(brandId =9 ,brandName="I", productId = 69,productCategory = ProductCategoryType.BAG.code, productPrice = 2400),
        Catalog(brandId =9 ,brandName="I", productId = 70,productCategory = ProductCategoryType.CAP.code, productPrice = 1700),
        Catalog(brandId =9 ,brandName="I", productId = 71,productCategory = ProductCategoryType.SOCKS.code, productPrice = 1700),
        Catalog(brandId =9 ,brandName="I", productId = 72,productCategory = ProductCategoryType.ACCESSORY.code, productPrice = 2400),
    )
    val sortedList = originalList.sortedWith(
        compareBy<Catalog> { it.productCategory }
            .thenBy { it.productPrice }
    )
    return sortedList
}