package com.sct.musinsa.assignment.catalog.persistence.domain.code

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class ProductCategoryType (val code: String, val koVal: String) {
    TOP("top","상의"),
    OUTER("outer","아우터"),
    BOTTOM("bottom","바지"),
    SNEAKERS("sneakers","스니커즈"),
    BAG("bag","가방"),
    CAP("cap","모자"),
    SOCKS("socks","양말"),
    ACCESSORY("accessory","액세서리");

    companion object {
        fun findByCode(code: String): ProductCategoryType {
            return ProductCategoryType.entries.find { it.code == code }
                ?: throw IllegalArgumentException("존재하지 않는 코드 입니다.")
        }
    }
}

@Converter
class ProductCategoryTypeConverter : AttributeConverter<ProductCategoryType, String> {
    override fun convertToDatabaseColumn(code: ProductCategoryType): String {
        return code.code
    }

    override fun convertToEntityAttribute(dbValue: String): ProductCategoryType? {
        return dbValue.let { ProductCategoryType.findByCode(dbValue) }
    }
}