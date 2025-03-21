package com.sct.musinsa.assignment.catalog.persistence.domain.entity

import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryType
import com.sct.musinsa.assignment.catalog.persistence.domain.code.ProductCategoryTypeConverter
import com.sct.musinsa.assignment.common.persistence.entity.audit.AuditEntity
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "product",
    uniqueConstraints = [UniqueConstraint(columnNames = ["brand_id","product_category"])])
class ProductEntity: AuditEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var id: Long? = null
    @Column(nullable = false, length = 32)
    @Convert(converter = ProductCategoryTypeConverter::class)
    var productCategory: ProductCategoryType = ProductCategoryType.TOP
    @Column(nullable = false)
    var productPrice: Long = 0L

    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var brand: BrandEntity? = null
        set(value) {
            field = value
            brand!!.add(this)
        }

}