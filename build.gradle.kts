plugins {
    `kotlin-dsl`             // Enables writing build logic in Kotlin DSL
    `java-gradle-plugin`     // Marks this as a Gradle plugin project
    `maven-publish`          // For publishing to Maven local/remote
}

group = "cz.projects.parizmat.eduHub"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        // Register plugin
        register("kotlin") {
            id = "cz.parizmat.eduHub.gradle.kotlin"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.kotlin.Kotlin"

        }

        // OpenAPI code generation plugin
        register("openApiGenerate") {
            id = "cz.parizmat.eduHub.gradle.openApi"
            implementationClass = "cz.project.eduHub.plugins.openApi.OpenApi"
        }

        // Detekt plugin wrapper
        register("detekt") {
            id = "cz.parizmat.eduHub.gradle.detekt"
            implementationClass = "cz.project.eduHub.plugins.detekt.Detekt"
        }

        //Kotlinter
        register("kotlinter") {
            id = "cz.parizmat.eduHub.gradle.kotlinter"
            implementationClass = "cz.project.eduHub.plugins.kotlinter.Kotlinter"
        }

        // SpringBoot
        register("spring-boot") {
            id = "cz.parizmat.eduHub.gradle.springBoot"
            implementationClass = "cz.project.eduHub.plugins.springBoot.SpringBoot"
        }
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // OpenAPI Generator plugin (used in custom OpenApi plugin)
    implementation("org.openapitools:openapi-generator-gradle-plugin:${property("openApi.version")}")

    // Detekt plugin (used inside Detekt plugin)
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${property("detekt.version")}")

    // Kotlin Gradle plugin API
    implementation(kotlin("gradle-plugin"))

    // Dokka plugin (documentation plugin)
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${property("dokka.version")}")

    // Kotlin plugins
    implementation("org.jetbrains.kotlin:kotlin-allopen:${property("kotlin.version")}") // set all classes to open
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${property("kotlin.version")}")
    implementation("org.jetbrains.kotlin:kotlin-noarg:${property("kotlin.version")}")

    // Kotlinter (formating code)
    implementation("org.jmailen.gradle:kotlinter-gradle:${property("kotlinter.version")}")

    //Spring boot
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${property("spring-boot.version")}")
    implementation("com.gorylenko.gradle-git-properties:gradle-git-properties:${property("git-properties.version")}")
}
kotlin {
    jvmToolchain(21)
}
