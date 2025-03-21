package com.sct.musinsa.assignment

import com.sct.musinsa.assignment.catalog.controller.payload.AdminCatalogCreateRequest
import com.sct.musinsa.assignment.catalog.service.AdminCatalogService
import com.sct.musinsa.assignment.common.util.JsonReader
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component


@SpringBootApplication
class MusinsaAssignmentApplication

fun main(args: Array<String>) {
    runApplication<MusinsaAssignmentApplication>(*args)
}

@Component
class CatalogDataLoader(
    private val adminCatalogService: AdminCatalogService
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val filePath = "io/all-brand-product-info.json" // 실제 JSON 파일 경로

        val requestList: List<AdminCatalogCreateRequest> =
            JsonReader().readFromJson(filePath)

        adminCatalogService.createCatalogs(requestList)
    }
}