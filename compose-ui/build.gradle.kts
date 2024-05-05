plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.jetbrains.compose)

    id("maven-publish")
}

// required by maven-publish plugin
val group: String by project
val version: String by project

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.jetbrains.navigation)
                implementation(project(":compose-navigation"))
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.framework.datatest)
            }
        }
        jvmTest {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}


publishing {
    repositories {
        gitlabMavenRepository()
    }
}

/**
 * common build utils
 */
fun RepositoryHandler.gitlabMavenRepository() {
    maven {
        val gitlabRepositoryProjectId: String by project
        url = uri("https://gitlab.com/api/v4/projects/$gitlabRepositoryProjectId/packages/maven")
        credentials(HttpHeaderCredentials::class) {
            name = "Job-Token"
            value = System.getenv("CI_JOB_TOKEN")
        }
        authentication {
            create("header", HttpHeaderAuthentication::class)
        }
    }
}

fun MavenPomDeveloperSpec.acrusage() {
    developer {
        id = "acrusage"
        name = "Stefan Kreiner"
        email = "borin_bickle@8alias.com"
    }
}