package cz.projects.parizmat.eduHub.plugins.springBoot

import com.gorylenko.GitPropertiesPluginExtension
import cz.projects.parizmat.eduHub.plugins.utils.addAllByPrefix
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByName
import org.springframework.boot.gradle.dsl.SpringBootExtension

class SpringBoot : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
        // Apply required plugins
        target.apply("cz.parizmat.eduHub.gradle.kotlin")
        target.apply("org.springframework.boot")
        target.apply("com.gorylenko.gradle-git-properties")

        // Configure Spring Boot build info
        target.extensions.configure<SpringBootExtension> {
            buildInfo()
        }

        // Disable default JAR task
        target.tasks.getByName<Jar>("jar") {
            enabled = false
        }

        // Configure Git properties plugin
        target.extensions.configure<GitPropertiesPluginExtension>("gitProperties") {
            failOnNoGitDirectory = true
        }

        // Add Spring Boot BOM
        libs.findLibrary("spring-boot-bom").let {
            target.dependencies.add("implementation", target.dependencies.platform(it))
        }

        // Add configuration processor
        libs.findLibrary("spring-boot-configuration-processor").let {
            target.dependencies.add("annotationProcessor", it)
        }

        // Add gradle-git-* dependencies
        target.dependencies.addAllByPrefix(target, "gradle-git-", configuration = "implementation")

        // (Optional) Add spring-boot-starter-* dependencies
        target.dependencies.addAllByPrefix(target, "spring-boot-starter-", configuration = "implementation")
    }
}
