package cz.projects.parizmat.eduHub.plugins.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider

fun DependencyHandler.implementsIfPresented(dependency: Provider<*>?, configuration: String = "implementation") {
    dependency?.let {
        add(configuration, it)
    } ?: print("Dependency '$dependency' was null, please check if it's right value.")
}

fun DependencyHandler.addAllByPrefix(
    project: Project,
    prefix: String,
    configuration: String = "implementation"
) {
    val libs = project.extensions
        .getByType(VersionCatalogsExtension::class.java)
        .named("libs")

    libs::class.members
        .filter {
            it.name.startsWith(prefix) &&
                    it.returnType.toString().contains("Provider<MinimalExternalModuleDependency>")
        }
        .forEach { member ->
            val dependency = member.call(libs) as? Provider<*>
            implementsIfPresented(dependency, configuration)
        }
}
