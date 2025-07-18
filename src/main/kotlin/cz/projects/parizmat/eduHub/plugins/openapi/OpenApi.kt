package cz.projects.parizmat.cz.projects.parizmat.eduHub.plugins.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

/**
 * DSL configuration for OpenAPI code generation.
 */
open class OpenApiSettings(
    var outputDir: String? = null,
    var inputDir: String? = null,
    var version: String? = null
)

/**
 * Plugin for automatic generation of API and model classes from an OpenAPI specification
 * using the OpenAPI Generator plugin. This plugin performs the following steps:
 */
class OpenApi : Plugin<Project> {
    override fun apply(project: Project) {
        // Variables for plugin
        val extensions = project.extensions.create("openApiGenerate", OpenApiSettings::class.java)

        val version = extensions.version
        val inputDirPath = extensions.inputDir ?: "${project.rootDir}/src/main/resources/eduHub-api-${version}"
        val outputDirPath = extensions.outputDir ?: "${project.rootDir}/build/generated"

        // Add OpenAPI plugin
        project.pluginManager.apply("org.openapi.generator")

        // Register task
        val task = project.tasks.register("openApiGenerate", GenerateTask::class.java){
            generatorName.set("kotlin-spring")
            inputSpec.set(inputDirPath)
            outputDir.set(outputDirPath)
            apiPackage.set("cz.projects.parizmat.eduHub.api")
            modelPackage.set("cz.projects.parizmat.eduHub.model")
            skipValidateSpec.set(false)
            configOptions.set(mapOf(
                "dateLibrary" to "java8",
                "useSpringBoot3" to "true",
                "interfaceOnly" to "true",
                "reactive" to "true"
            ))
        }


        // Evaluate this after configuration
        project.afterEvaluate {

            // Waiting after compilations
            project.tasks.named("compileKotlin").configure {
                dependsOn(task)
            }

            // Add generated source into main Kotlin sourceDir
            project.extensions.configure<KotlinProjectExtension>("kotlin"){
                sourceSets.getByName("main").kotlin.srcDirs("$outputDirPath/src/main/kotlin")
            }
        }
    }
}

