package cz.projects.parizmat.eduHub.plugins.dokka

import cz.projects.parizmat.eduHub.plugins.utils.addAllByPrefix
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class Dokka : Plugin<Project> {
    override fun apply(target: Project) {
        // Apply Dokka plugin
        target.apply(plugin = "org.jetbrains.dokka")

        // Add Dokka plugin dependency from version catalog
        target.dependencies.addAllByPrefix(target, "dokka-", configuration = "dokkaPlugin")
    }
}
