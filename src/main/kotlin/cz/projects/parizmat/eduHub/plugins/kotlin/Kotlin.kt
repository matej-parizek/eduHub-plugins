package cz.projects.parizmat.cz.projects.parizmat.eduHub.plugins.kotlin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

open class KotlinSettings(
    val sourceVersion: JavaVersion = JavaVersion.VERSION_17,
    val targetVersion: JavaVersion = JavaVersion.VERSION_17,
    val kotlinToolchain: Int = 17
)

class Kotlin : Plugin<Project> {
    override fun apply(project: Project) {

        val extension = project.extensions.create("kotlin", KotlinSettings::class.java)
        val sourceVersion = extension.sourceVersion
        val targetVersion = extension.targetVersion

        project.pluginManager.apply("org.jetbrains.kotlin.jvm")
        project.pluginManager.apply("jacoco")

        project.extensions.configure<JavaPluginExtension>("java") {
            sourceCompatibility = sourceVersion
            targetCompatibility = targetVersion
        }

        project.extensions.configure<KotlinJvmProjectExtension>("kotlin") {
            jvmToolchain(extension.kotlinToolchain)

            compilerOptions {
                freeCompilerArgs.add("-Xjsr305=strict")
            }
        }

        project.tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }

        project.tasks.getByName<JacocoReport>("jacocoTestReport") {
            dependsOn(project.tasks.withType<Test>())

            reports{
                xml.required.set(true)
            }
        }

        project.configurations.matching { it.name == "compileOnly" }.configureEach{
            this.extendsFrom(project.configurations.getByName("annotationProcessor"))
        }
    }
}