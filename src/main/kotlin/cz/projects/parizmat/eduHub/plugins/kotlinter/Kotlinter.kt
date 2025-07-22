package cz.projects.parizmat.cz.projects.parizmat.eduHub.plugins.kotlinter

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

        target.project.apply("org.jmailen.kotlinter")
        target.extensions.configure<KotlinterExtension>{
            reporters = arrayOf("html")
        }

        target.tasks.withType<LintTask>().configureEach {
            source =source.minus(target.fileTree(
                target.layout.buildDirectory.dir("generated-src"))
            ).asFileTree
        }

        target.tasks.withType<FormatTask>().configureEach {
            source = source.minus(target.fileTree(
                target.layout.buildDirectory.dir("generated-src"))
            ).asFileTree
        }
    }
}