import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
}

group = "com.moa"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {

    /* Spring Dep --> */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    /* <-- Spring Dep */

    /* Database Dep --> */
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    /* <-- Database Dep */

    /* Kotlin Dep --> */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    /* <-- Kotlin Dep */

    /* Extra Dep --> */
    /* <-- Extra Dep */

    /* Test Dep --> */
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.mockito:mockito-core:3.3.3")
    testImplementation("io.mockk:mockk:1.10.3")
    /* <-- Test Dep */

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
