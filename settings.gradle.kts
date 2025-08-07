// Set the name of the Gradle root project
rootProject.name = "edu-plugins"

// Plugin management for resolving Gradle plugins
pluginManagement {
    plugins {
        // Automatically resolves and installs the right JDK via Foojay
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    }
    repositories {
        gradlePluginPortal() // For Gradle community plugins
        mavenCentral()       // For standard Maven dependencies
    }
}

// Centralized dependency resolution for all projects
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    // Version catalog definition MUST be inside dependencyResolutionManagement
    versionCatalogs {
        create("libs") {
            files("gradle/libs.versions.toml")
        }
    }
}
