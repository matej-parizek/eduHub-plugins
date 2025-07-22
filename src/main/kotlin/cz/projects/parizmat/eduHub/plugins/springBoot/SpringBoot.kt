package cz.projects.parizmat.cz.projects.parizmat.eduHub.plugins.springBoot

import com.gorylenko.GitPropertiesPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByName
import org.springframework.boot.gradle.dsl.SpringBootExtension

class SpringBoot : Plugin<Project> {
    private lateinit var _target: Project
    override fun apply(target: Project) {
        _target = target
        target.project.apply("cz.parizmat.eduHub.gradle.kotlin")
        target.project.apply("org.springframework.boot")
        target.project.apply("com.gorylenko.gradle-git-properties")

        target.project.extensions.configure<SpringBootExtension> {
            buildInfo()
        }

        target.project.tasks.getByName<Jar>("jar") {
            enabled = false
        }

        target.project.extensions.configure<GitPropertiesPluginExtension>("gitProperties") {
            failOnNoGitDirectory = true
        }

        target.dependencies.add(
            "annotationProcessor",
            "org.springframework.boot:spring-boot-configuration-processor"
        )

        target.project.extensions.extraProperties.apply {
            target.project.setVersion("kotest-extensions-spring.version", "1.1.3")
            target.project.setVersion("snakeyaml.version", "2.2")
            target.project.setVersion("spring-boot.version", "3.1.4")
            target.project.setVersion("springmockk.version", "4.0.2")
        }


        target.dependencies.add(
            "implementation",
            target.dependencies.platform(
                "org.springframework.boot:spring-boot-dependencies:${target.property("spring-boot.version")}"
            )
        )
    }

    fun Project.setVersion(key: String, default: String) {
        _target.extensions.extraProperties.set(key, findProperty(key) ?: default)
    }
}