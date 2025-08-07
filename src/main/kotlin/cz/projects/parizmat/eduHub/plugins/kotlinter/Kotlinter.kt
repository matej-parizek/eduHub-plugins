package cz.projects.parizmat.eduHub.plugins.kotlinter

import cz.projects.parizmat.eduHub.plugins.utils.addAllByPrefix
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jmailen.gradle.kotlinter.KotlinterExtension
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

class Kotlinter : Plugin<Project> {
    override fun apply(target: Project) {
        // Apply the plugin
        target.apply(plugin = "org.jmailen.kotlinter")

        // Add dependency from version catalog
        target.dependencies.addAllByPrefix(target, "kotlinter-", configuration = "implementation")

        // Configure Kotlinter
        target.extensions.configure<KotlinterExtension> {
            reporters = arrayOf("html")
        }

        // Exclude generated sources from lint and format
        target.tasks.withType<LintTask>().configureEach {
            source = source.minus(
                target.fileTree(target.layout.buildDirectory.dir("generated-src"))
            ).asFileTree
        }

        target.tasks.withType<FormatTask>().configureEach {
            source = source.minus(
                target.fileTree(target.layout.buildDirectory.dir("generated-src"))
            ).asFileTree
        }
    }
}
