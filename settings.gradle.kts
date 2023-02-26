@file:Suppress("UnstableApiUsage")


pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.version.toml"))
        }
        create("apps") {
            from(files("gradle/apps.versions.toml"))
        }
    }
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Rickpedia"

include(":core:data")
include(":core:database")
include(":core:network")
include(":core:designsystem")
include(":app")
