plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    val kotlinVersion = "1.9.22"
    kotlin("plugin.jpa") version kotlinVersion
}

group = "iut.nantes.project"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
    implementation("org.springframework.boot:spring-boot-starter-validation")


    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.1")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
