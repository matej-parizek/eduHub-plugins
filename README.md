# EduHubPlugin

**EduHubPlugin** is a centralized Gradle plugin suite designed to support Kotlin-based microservices in the EduHub project. It encapsulates shared Gradle logic, code quality tools, Spring Boot support, and OpenAPI code generation to enforce consistency and reduce boilerplate across services.

## 📦 Project Info

- **Group**: `cz.projects.parizmat.eduHub`
- **Version**: `1.0-SNAPSHOT`
- **Plugins defined**: `kotlin`, `spring-boot`, `detekt`, `openApiGenerate`, `kotlinter`

---

## ✨ Features

This plugin project provides reusable build logic for all microservices within the eduHub ecosystem:

### ✅ Included Plugins

| Plugin ID                                  | Description                                                                 |
|--------------------------------------------|-----------------------------------------------------------------------------|
| `cz.parizmat.eduHub.gradle.kotlin`         | Configures Kotlin and Dokka; sets JVM toolchain and compiler options.      |
| `cz.parizmat.eduHub.gradle.springBoot`     | Applies Spring Boot plugin and configures metadata like Git properties.    |
| `cz.parizmat.eduHub.gradle.detekt`         | Applies [Detekt](https://detekt.dev/) static analysis with configurable rules. |
| `cz.parizmat.eduHub.gradle.openApi`        | Enables [OpenAPI Generator](https://openapi-generator.tech/) integration for code generation. |
| `cz.parizmat.eduHub.gradle.kotlinter`      | Applies [Kotlinter](https://github.com/jeremymailen/kotlinter-gradle) for Kotlin formatting. |

---

## 🛠 Used Technologies

This plugin project utilizes the following dependencies:

| Tool/Library                  | Purpose                        |
|------------------------------|--------------------------------|
| Kotlin DSL                   | Write type-safe Gradle logic   |
| Detekt                       | Static code analysis for Kotlin|
| Kotlinter                    | Auto-format Kotlin code        |
| Dokka                        | Generate Kotlin documentation  |
| Spring Boot Plugin           | Spring Boot project support    |
| Git Properties Plugin        | Git metadata in JARs           |
| OpenAPI Generator Plugin     | API client/server code generation from OpenAPI spec |

---

## 🚀 Usage in Microservice Projects

To use any of the plugins from this suite in your microservice project, simply include the appropriate plugin ID in your `build.gradle.kts`:

```kotlin
plugins {
    id("cz.parizmat.eduHub.gradle.kotlin")
    id("cz.parizmat.eduHub.gradle.springBoot")
    id("cz.parizmat.eduHub.gradle.detekt")
    id("cz.parizmat.eduHub.gradle.openApi")
    id("cz.parizmat.eduHub.gradle.kotlinter")
}
```

Make sure to add the `EduHubPlugin` to your `pluginManagement` block or `buildSrc` if it's locally developed.

---

## 🧱 Building and Publishing

You can build the plugin using:

```bash
./gradlew build
```

To publish locally for development:

```bash
./gradlew publishToMavenLocal
```

For remote publishing, configure the `maven-publish` plugin with your repository credentials.

---

## 📝 Notes

- Requires **JDK 21**
- Assumes versions are provided via Gradle properties (e.g. `openApi.version`, `detekt.version`, etc.)

---
