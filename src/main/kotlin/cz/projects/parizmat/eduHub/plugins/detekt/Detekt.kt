package cz.projects.parizmat.cz.projects.parizmat.eduHub.plugins.detect

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

import java.io.File

/**
 * DSL configuration for Detekt code generation.
 */
open class DetectSettings(
    var configFilePath: String? = null,
    var sourcePath: List<String> = emptyList(),
    var baselineFile: File? = null,
    var html: Boolean = true,
    var xml: Boolean = false,
    var sarif: Boolean = false,
    var txt: Boolean = false,
    var md: Boolean = false
)



/**
 * Gradle plugin for configuring Detekt static code analysis in a Kotlin project.
 *
 * This plugin allows for declarative configuration using the `detect` DSL extension
 * and sets up Detekt to run with custom or default parameters.
 */
class Detekt : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("detect", DetectSettings::class.java)

        val sourcePath = extension.sourcePath.ifEmpty { listOf("src/main/kotlin", "src/test/kotlin") }
        val configPath = extension.configFilePath ?: "${project.rootDir}/config/detekt/config.yml"
        val baselineFile = extension.baselineFile ?: project.file("${project.rootDir}/config/detekt/baseline.xml")

        project.pluginManager.apply("io.gitlab.arturbosch.detekt")

        project.extensions.configure<DetektExtension> {
            config.setFrom(configPath)
            source.setFrom(sourcePath)
            baseline = baselineFile
            buildUponDefaultConfig = true
            parallel = true
        }
        project.tasks.withType<Detekt>().configureEach {
            source = source.minus(
                project
                    .fileTree(project.layout.buildDirectory.dir("generated"))
            ).asFileTree
            reports {
                html.required.set(extension.html)
                xml.required.set(extension.xml)
                sarif.required.set(extension.sarif)
                txt.required.set(extension.txt)
                md.required.set(extension.md)
            }
        }
        project.tasks.withType<DetektCreateBaselineTask>().configureEach {
            source = source.minus(
                project.fileTree(
                    project.layout
                        .buildDirectory.dir("generated")
                )
            ).asFileTree
        }
    }
}