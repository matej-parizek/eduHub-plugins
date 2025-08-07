plugins {
    `kotlin-dsl`             // Enables Kotlin DSL syntax for Gradle build scripts
    `java-gradle-plugin`     // Allows defining and publishing custom Gradle plugins
    `maven-publish`          // Enables publishing artifacts to Maven repositories
}

group = "cz.projects.parizmat.eduHub"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        // Custom plugin for Kotlin compiler settings
        register("kotlin") {
            id = "cz.projects.parizmat.eduHub.gradle.kotlin"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.kotlin.Kotlin"
        }

        // Plugin for generating code from OpenAPI specs
        register("openApiGenerate") {
            id = "cz.projects.parizmat.eduHub.gradle.openApi"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.openapi.OpenApi"
        }

        // Plugin for Detekt static code analysis
        register("detekt") {
            id = "cz.projects.parizmat.eduHub.gradle.detekt"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.detekt.Detekt"
        }

        // Plugin for Kotlinter code formatting
        register("kotlinter") {
            id = "cz.projects.parizmat.eduHub.gradle.kotlinter"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.kotlinter.Kotlinter"
        }

        // Plugin for configuring Spring Boot in a standard way
        register("springBoot") {
            id = "cz.projects.parizmat.eduHub.gradle.springBoot"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.springBoot.SpringBoot"
        }

        // Plugin for generating documentation using Dokka
        register("dokka") {
            id = "cz.projects.parizmat.eduHub.gradle.dokka"
            implementationClass = "cz.projects.parizmat.eduHub.plugins.dokka.Dokka"
        }
    }
}

repositories {
    mavenCentral()         // Core Java, Kotlin, and Spring dependencies
    gradlePluginPortal()   // Required for Gradle plugin resolution (e.g., kotlinter, dokka, detekt)
}

// Create custom configuration for publishing version catalog
val versionCatalog by configurations.creating

// Publish the version catalog file as a Maven artifact
artifacts {
    add("versionCatalog", file("gradle/libs.versions.toml")) {
        builtBy(tasks.named("jar"))
        type = "toml"
        extension = "toml"
        classifier = "libs"
    }
}

publishing {
    publications {
        create<MavenPublication>("versionCatalog") {
            artifactId = "edu-plugins-catalog"
            version = project.version.toString()

            artifact(file("gradle/libs.versions.toml")) {
                extension = "toml"
                classifier = "libs"
            }
        }
    }

    repositories {
        mavenLocal() // Publishes to local Maven repo (~/.m2)
    }
}

dependencies {
    // Load the version catalog
    val libs = project.extensions
        .getByType(VersionCatalogsExtension::class.java)
        .named("libs")
    // === Plugin APIs needed to compile your custom plugins ===

    implementation(libs.findLibrary("kotlin-gradlePlugin").get())      // kotlin("gradle-plugin")
    implementation(libs.findLibrary("kotlin-allopen").get())           // @AllOpen support
    implementation(libs.findLibrary("kotlin-noarg").get())             // @NoArg support

    implementation(libs.findLibrary("detekt-gradle-plugin").get())     // Detekt plugin APIs
    implementation(libs.findLibrary("kotlinter-gradle").get())         // Kotlinter plugin APIs
    implementation(libs.findLibrary("dokka-gradle-plugin").get())      // Dokka plugin APIs
    implementation(libs.findLibrary("openapi-generator-gradle-plugin").get()) // OpenAPI generator

    implementation(libs.findLibrary("spring-boot-gradle-plugin").get())    // Spring Boot Gradle plugin
    implementation(libs.findLibrary("gradle-git-properties").get())        // Git commit info plugin
}

// Automatically use JDK 21 with Foojay-managed toolchain
kotlin {
    jvmToolchain(21)
}
