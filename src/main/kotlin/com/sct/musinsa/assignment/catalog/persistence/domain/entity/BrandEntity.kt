package com.sct.musinsa.assignment.catalog.persistence.domain.entity

import com.sct.musinsa.assignment.common.persistence.entity.audit.AuditEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "brand",
    uniqueConstraints = [UniqueConstraint(columnNames = ["brandName"])]
)
class BrandEntity: AuditEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    var id: Long? = null
    @Column(nullable = false, length = 32)
    var brandName: String? = ""

    @OneToMany(mappedBy = "brand", cascade = [CascadeType.ALL])
    var products: MutableList<ProductEntity> = mutableListOf()

    fun add(product: ProductEntity) {
        this.products.add(product)
    }
}