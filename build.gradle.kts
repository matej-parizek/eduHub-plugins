plugins {
    `kotlin-dsl`             // Enables writing build logic in Kotlin DSL
    `java-gradle-plugin`     // Marks this as a Gradle plugin project
    `maven-publish`          // Optional: for publishing to Maven local/remote
}

group = "cz.projects.parizmat.eduHub"
version = "1.0-SNAPSHOT"

gradlePlugin{
    plugins{
        // Register plugin
        register("openApiGenerate"){
            id="cz.projects.parizmat.eduHub.plugins.openApi"
            implementationClass = "cz.project.eduHub.plugins.openApi.OpenApi"
        }

        register("detect"){
            id="cz.projects.parizmat.eduHub.plugins.detekt"
            implementationClass = "cz.project.eduHub.plugins.detekt.DeteKt"

        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // OpenAPI
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.6.0")

    //Detect (Static analyzation of code)
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")

    implementation(kotlin("gradle-plugin"))
    implementation("org.slf4j:slf4j-api:2.0.13") // Logger
    implementation(kotlin("stdlib"))


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}