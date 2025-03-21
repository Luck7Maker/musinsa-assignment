plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("kapt") version "1.9.24"
}

group = "com.sct.musinsa"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion = "5.8.0"
    val mockkVersion = "1.13.10"
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Open api(Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    // H2
    runtimeOnly("com.h2database:h2")
    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    //Query DSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    // QueryDSL APT 의존성
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    // Jakarta Annotation API 의존성
    kapt("jakarta.annotation:jakarta.annotation-api:1.3.5")
    // Jakarta Persistence API 의존성
    kapt("jakarta.persistence:jakarta.persistence-api:2.2.3")

    //Test
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation ("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation ("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation ("io.kotest:kotest-property:$kotestVersion")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
